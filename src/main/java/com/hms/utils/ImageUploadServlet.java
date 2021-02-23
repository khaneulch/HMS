package com.hms.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ckEditor 이미지 업로드
 */
@WebServlet("/ckImageUpload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, 	// 메모리 저장 최대 크기 : 2MB
		maxFileSize = 1024 * 1024 * 1000, 				// 파일당 최대 파일 크기 : 100MB 
		maxRequestSize = 1024 * 1024 * 50) 				// 요청당 최대 크기 : 500MB
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ImageUploadServlet.class);
	private static String rootPath = GetPropertyUtils.getProperty("file.root.path");
	private static String uploadFolder = GetPropertyUtils.getProperty("upload.folder");
	
	public ImageUploadServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		JSONObject jsonObject = new JSONObject();
		
		String separator = File.separator;
        String filePath = rootPath + uploadFolder;
        File dir = new File(filePath);
 
        // 파일에 크기가 메모리 저장 최대 크기(fileSizeThreshold)가 아닌 경우  메모리에 저장
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        // 메모리 저장 최대 크기를 넘길 경우 파일을 생성할 임시 디렉터리를 지정
        fileItemFactory.setRepository(dir);
        
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        
        try {
        	// FileItem : 전송된 파라미터 또는 파일 정보를 저장하고 있음
            Map<String, List<FileItem>> fileItemMap = servletFileUpload.parseParameterMap(request);
            
            // 컴포넌트별 서브 파일 경로
            String editorPath = request.getParameter("filePath");
            filePath = filePath + separator + editorPath;
            
            List<FileItem> fileItemList = fileItemMap.get("upload");
            if( fileItemList != null) {
	            for ( FileItem fileItem : fileItemList) {
	                if ( fileItem.isFormField()) {
	                    // 파일이 아닌경우
	                } else {
	                    if ( fileItem.getSize() > 0) {
	                    	logger.debug("FILE NAME : " + fileItem.getName());
	                    	logger.debug("FILE SIZE : " + fileItem.getSize());
	                        
	                        int index =  fileItem.getName().lastIndexOf(separator);
	                        
	                        String fileOrgName = fileItem.getName().substring(index  + 1);		// 원본 파일명
	                        String ext = fileOrgName.substring(fileOrgName.indexOf("."));		// 파일 확장명
	                        String newName = CommonUtils.getSavedFileName() + ext;				// 저장 파일명
	                        
	                        File fileDir = new File(filePath);
	                        if( !fileDir.exists()) fileDir.mkdirs();
	                        
	                        File uploadFile = new File(filePath +  separator + newName);
	                        // 파일을 저장
	                        fileItem.write(uploadFile);
	                        
	                        // ckeditor에 리턴할 내용
	                        jsonObject.put("uploaded", 1);
	                        jsonObject.put("url", uploadFolder + separator + editorPath + separator + newName);
	                        jsonObject.put("fileName", newName);
	                    }
	                }
	            }
            }
    		
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().print(jsonObject.toString());
 
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.debug(e.getMessage());
        }
	}
}