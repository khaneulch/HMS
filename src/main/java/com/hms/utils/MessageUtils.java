package com.hms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageUtils {
	/** 운영 : https://api.bizppurio.com, 개발 : https://dev-api.bizppurio.com:10443 **/
	private static String ppurioUrl = "https://dev-api.bizppurio.com:10443/v2/message";
	private static String ppurioAccount = "test_daelim3";
	private static String ppurioRefkey = "daelim777**";
	private static String ppurioSendFrom = "07000000000";
	private static String ppurioSenderKey = "02fcfea32b9ebb9d3eff552b5ff9cdc4485d0779";
	
	private static String ECS_TEST_01 = "[대림디움] 주문안내 \\n안녕하세요. %s\\n\\n"
			+ "대림비앤코 제품을 구매해 주셔서 감사합니다.\\n\\n"
			+ "*주문일자: %s\\n*주문번호: %s \\n*제품명 : %s\\n\\n"
			+ "아래 링크를 클릭하여 세부내용을 확인 하시기 바랍니다.\\n\\n"
			+ "▶공급내역서 확인/서명 바로가기\\n\\n"
			+ "%s\\n"
			+ "☎대표전화:1588-4360\\n\\n"
			+ "감사합니다.";

	public static void main(String[] args) {
		//sendSms("aaaaa", "01000000000");
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("nm", "이봉헌");
		m.put("orDt", "2020-10-11");
		m.put("orderNo", "ECO0000001");
		m.put("productNm", "상품명");
		m.put("shortUrl", "www.naver.com");
		m.put("mph", "01063127830");
		
		sendAt(m, "ECS_TEST_01");
		
	}
	
	/**
	 * sms 데이터 생성 및 전송
	 * @param message
	 * @param mph
	 */
	public static void sendSms(String message, String mph) {
		JSONObject sendMap = new JSONObject();
		sendMap.put("account", ppurioAccount);
		sendMap.put("refkey", ppurioRefkey);
		sendMap.put("type", "sms");
		sendMap.put("from", ppurioSendFrom);
		sendMap.put("to", mph);						// 받는 전화번호
		
		JSONObject contentMap = new JSONObject();
		JSONObject smsMap = new JSONObject();
		smsMap.put("message", message);				// 보낼 메세지
		contentMap.put("sms", smsMap);
		sendMap.put("content", contentMap);
			
		sendTask(sendMap.toString());
	}
	
	/**
	 * 알림톡 데이터 생성 및 전송
	 * @param param
	 * @param templateCode
	 */
	public static void sendAt(Map<String, String> param, String templateCode) {
		JSONObject sendMap = new JSONObject();
		sendMap.put("account", ppurioAccount);
		sendMap.put("refkey", ppurioRefkey);
		sendMap.put("type", "at");
		sendMap.put("from", ppurioSendFrom);
		sendMap.put("to", param.get("mph"));		// 받는 전화번호
		
		JSONObject contentMap = new JSONObject();
		JSONObject atMap = new JSONObject();
		atMap.put("senderkey", ppurioSenderKey);	
		atMap.put("templatecode", templateCode);	// 템플릿
		String message = "";
		if( templateCode.equals("ECS_TEST_01")) {
			message = String.format(ECS_TEST_01
					, param.get("nm")				// 이름
					, param.get("orDt")				// 주문일자
					, param.get("orderNo")			// 주문번호
					, param.get("productNm")		// 상품명
					, param.get("shortUrl")			// 링크주소
			);
		} 
		atMap.put("message", message);				// 보낼 메세지
		contentMap.put("at", atMap);
		sendMap.put("content", contentMap);
		
		sendTask(sendMap.toString());
	}

	
	/**
	 * 전송 로직
	 * @param objectString
	 * @return
	 */
	public static boolean sendTask(String objectString) {
		String input = null;
		StringBuffer result = new StringBuffer();
		URL url = null;

		try {
			if( objectString != null && objectString != "") {
				/** SSL 인증서 무시 : 비즈뿌리오 API 운영을 접속하는 경우 해당 코드 필요 없음 **/
				TrustManager[] trustAllCerts = new TrustManager[] { 
						new X509TrustManager() {
							public X509Certificate[] getAcceptedIssuers() { return null;}
							public void checkClientTrusted(X509Certificate[] chain, String authType) {}
							public void checkServerTrusted(X509Certificate[] chain, String authType) {}
						}
				};
	
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	
				url = new URL( ppurioUrl);
	
				/** Connection 설정 **/
				HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.addRequestProperty("Content-Type", "application/json");
				connection.addRequestProperty("Accept-Charset", "UTF-8");
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setUseCaches(false);
				connection.setConnectTimeout(15000);
	
				/** Request **/
				OutputStream os = connection.getOutputStream();
				os.write(objectString.getBytes("UTF-8"));
				os.flush();
	
				/** Response **/
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				while ((input = in.readLine()) != null) {
					result.append(input);
				}
				connection.disconnect();
				
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> response = mapper.readValue(result.toString(), Map.class);
				System.out.println(result.toString());
				
				int code = (int) response.get("code");
				if( code == 1000) {	// 성공
					return true;
				} else {
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
}