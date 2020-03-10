package com.sesoc.web7.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupParser {

	public static void main(String[] args) {

		String surl = "https://job.rikunabi.com/2021/company/r652800038/employ/";
		Document doc = null;

		try {
			doc = Jsoup.connect(surl).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements element = doc.select("div.ts-h-company-media02-main");
		String elStr = element.text();
		String[] strArr = elStr.split("・");

//		for (int i = 0; i < strArr.length; i++) {
//			System.out.println(strArr[i]);
//		}

		String clientId = "9VLCjOECDMTITU4FwWNG";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "nNz9z7RPL_";// 애플리케이션 클라이언트 시크릿값";
		try {
			String text = URLEncoder.encode(elStr, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			// post request
			String postParams = "source=ja&target=ko&text=" + text;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			JSONParser parser = new JSONParser();
			
			try {
				JSONObject jsonObject = (JSONObject)parser.parse(inputLine);
				System.out.println(jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
