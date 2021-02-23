package com.hms.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import com.hms.main.impl.MainService;

public class HmsFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(HmsFileUtils.class);
	
	private static String rootPath = GetPropertyUtils.getProperty("file.root.path");
	private static String uploadFolder = GetPropertyUtils.getProperty("upload.folder");
	
	@Autowired
    ServletContext context;
	
	public static void deleteFile(String fileName) {
		deleteFile(fileName, "");
	}
	
	public static void deleteFile(String fileName, String path) {
		File file = new File(rootPath + uploadFolder + "/" + path, fileName);
		if( file.exists()) file.delete();
	}
	
	public static File uploadFile(MultipartFile mFile) {
		return uploadFile(mFile, "");
	}
	
	/**
	 * 파일 업로드
	 * @param mFile
	 * @param path
	 * @return
	 */
	public static File uploadFile(MultipartFile mFile, String path) {
		
		File target = null;
		
		try {
			String orgFileName = mFile.getOriginalFilename();
			
			byte[] fileData = mFile.getBytes();
		
			String ext = orgFileName.substring(orgFileName.lastIndexOf("."));
			
			String savedName = CommonUtils.getSavedFileName();
			
			File dir = new File(rootPath + uploadFolder + "/" + path);
			if( !dir.exists()) dir.mkdirs();
			
			target = new File(rootPath + uploadFolder + "/" + path, savedName + ext);
			
			FileCopyUtils.copy(fileData, target);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return target;
	}
	
	@SuppressWarnings("finally")
	public static String downloadZip(
			HttpServletRequest request
			, HttpServletResponse response
			, Map<String, Object> params) throws Exception {
		FileInputStream fis = null;
        String html = params.getOrDefault("data", "") + "";
        String zipFileName = "";
		
		try {
			if( !html.equals("")) {
				
				// 이미지 경로를 수정한다.
				html = html.replaceAll("/upload", "./upload");
				html = html.replaceAll("/content/hms/js", "./js");
				html = html.replaceAll("/content/hms/images", "./images");
				
				ServletContext context = request.getServletContext();
				String contentPath = context.getRealPath("/content/hms/");
				
				// 스크립트를 포함한 jsp 파일을 읽어온다.
				String jspPath = context.getRealPath("/WEB-INF/views/main/");
				String jsp = getFile(jspPath, "main_view_export.jsp");
				
				// 스크립트와 request로 넘어온 html을 합친다.
				StringBuffer sb = new StringBuffer();
				sb.append(jsp);
				sb.append(html);
				
				// 메인화면의 HTML 파일을 만든다.
				String htmlFileName = CommonUtils.getSavedFileName();
				String fileFullPath = contentPath + htmlFileName + ".html";
				System.out.println(fileFullPath);
				File htmlFile = new File(fileFullPath);
				
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
				writer.write(sb.toString());
				writer.close();
				
				// upload 폴더를 content/hms 하위의 폴더에 복사한다.
				List<File> uploadFileList = uploadFileCopy(contentPath, params);
				
				// content/hms 하위의 폴더를 압축한다.
				File zipFolder = new File(rootPath + "/zip/");
				if( !zipFolder.exists()) zipFolder.mkdirs();
				zipFileName = CommonUtils.getSavedFileName();
				
				compress(contentPath, rootPath + "/zip/" + zipFileName + "_export.zip");
				
				// 압축후 생성된 HTML 파일을 지운다.
				if( htmlFile.exists()) htmlFile.delete();
				
				// 압축후 복사된 upload 폴더를 지운다.
				if( uploadFileList != null && uploadFileList.size() > 0) {
					for( File uploadFile : uploadFileList) {
						if( uploadFile.exists()) {
							for( File f : uploadFile.listFiles()) {
								f.delete();
							}
							uploadFile.delete();
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch(Exception e) { e.printStackTrace();}
			}
			return zipFileName;
		}
	
	}
	
	/**
	 * 컴포넌트에 해당하는 업로드 폴더/로고 폴더를 복사한다.
	 * @param contentPath
	 * @param params 
	 * @return
	 * @throws Throwable
	 */
	public static List<File> uploadFileCopy(String contentPath, Map<String, Object> params) throws Throwable{
		List<File> fileList = new ArrayList<File>();
		String logoPath = rootPath + uploadFolder + "/logo";
		File logoFile = new File(contentPath + "upload/logo");
		FileUtils.copyDirectory(new File(logoPath), logoFile);
		
		fileList.add(logoFile);
		
		MainService service = (MainService) CommonUtils.serviceLoader("mainService");
		List<Map<String, Object>> list = service.searchFilePath(params);
		
		for( Map<String, Object> m : list) {
			String compPath = rootPath + uploadFolder + "/" + m.get("filePath");
			File compFile = new File(contentPath + "upload/" + m.get("filePath"));
			FileUtils.copyDirectory(new File(compPath), compFile);
			fileList.add(compFile);
		}
		
		return fileList;
	}
	
	/**
	 * 경로의 폴더를 압축한다.
	 * @param path
	 * @param outputFileName
	 * @throws Throwable
	 */
	public static void compress(String path, String outputFileName) throws Throwable{
		File file = new File(path);
		int pos = outputFileName.lastIndexOf(".");
		
		if( !outputFileName.substring(pos).equalsIgnoreCase(".zip")) {
			outputFileName += ".zip";
		}
		
		if( !file.exists()) {
			throw new Exception("Not File!");
		}
		
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		
		try {
			fos = new FileOutputStream(new File(outputFileName));
			zos = new ZipOutputStream(fos);
			zos.setLevel(8);
			searchDirectory(file,zos);
		   
		} catch(Throwable e){
			throw e;
		} finally{
			if(zos != null) zos.close();
			if(fos != null) fos.close();
		}
	}
	
	private static void searchDirectory(File file, ZipOutputStream zos) throws Throwable{
		searchDirectory(file, file.getPath(), zos);
	}
	
	/**
	 * 디렉토리 탐색
	 * @param file
	 * @param root
	 * @param zos
	 * @throws Exception
	 */
	private static void searchDirectory(File file, String root, ZipOutputStream zos) throws Exception{
		if( file.isDirectory()) {
			for( File f : file.listFiles()){
				searchDirectory(f, root, zos);
			}
		} else{
			compressZip(file, root, zos);
		}
	}
	
	/**
	* 압축 메소드
	* @param file
	* @param root
	* @param zos
	* @throws Exception
	*/
	private static void compressZip(File file, String root, ZipOutputStream zos) throws Exception{
		FileInputStream fis = null;
		
		try {
			String zipName = file.getPath().replace(root+"\\", "");
			fis = new FileInputStream(file);
			
			ZipEntry zipentry = new ZipEntry(zipName);
			zos.putNextEntry(zipentry);
			
			int length = (int)file.length();
			byte[] buffer = new byte[length];
			
			fis.read(buffer, 0, length);
			zos.write(buffer, 0, length);
			zos.closeEntry();
		} catch(Throwable e){
			throw e;
		} finally{
			if(fis != null) fis.close();
		}
	}
	
	/**
	 * 경로에서 파일을 읽어온다.
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	private static String getFile (String filePath, String fileName) {
        FileInputStream fileInputStream = null;
        StringBuffer sb = new StringBuffer();
        
        try {
        	fileInputStream = new FileInputStream( filePath + fileName );
        	
	        byte[] readBuffer = new byte[fileInputStream.available()];
	        
			while (fileInputStream.read( readBuffer ) != -1) {
				sb.append(new String(readBuffer));
			}
			
	        fileInputStream.close();
	        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sb.toString();
	}

	/**
	 * 생성된 홈페이지 zip파일을 다운받는다.
	 * @param zipFileName
	 * @param response
	 */
	public static void zipDownload(String zipFileName, HttpServletResponse response) {
		response.setContentType("application/octet-stream; charset=utf-8");
	    response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName + "_export.zip;");
	    
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	    
	    try {
	    	logger.debug("DOWNLOAD ZIPFILE : " + zipFileName + "_export.zip");
	    	
	    	File file = new File(rootPath + "/zip/" + zipFileName + "_export.zip");
	    
		    int nRead = 0;
		    byte btReadByte[] = new byte[(int)file.length()];
	
		    if( file.length() > 0 && file.isFile()) {
		        bis = new BufferedInputStream( new FileInputStream(file));
		        bos = new BufferedOutputStream( response.getOutputStream());

		        while( ( nRead = bis.read(btReadByte)) != -1) {
		        	bos.write(btReadByte, 0, nRead);
		        }
		    }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if( bos != null) bos.flush();
	    		if( bos != null) bos.close();
	    		if( bis != null) bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}