package com.sesoc.web7.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;
import org.snu.ids.kkma.util.Timer;

public class JsoupParser {

	Logger log = LoggerFactory.getLogger(JsoupParser.class);
	public static String getResponse(String cc) throws IOException {
		// 인물상 크롤링
		String surl = "https://job.rikunabi.com/2021/company/"+ cc +"/employ/";
		Document doc = null;

		try {
			doc = Jsoup.connect(surl).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements element = doc.select("div.ts-h-company-media02-main");
		String elStr = element.text();
		return elStr;
	}
	//크롤링용 임시 메서드
//	public static void main(String[] args) {
//		String surl = "https://job.rikunabi.com/2021/company/r806010018/employ/";
//		Document doc = null;
//
//		try {
//			doc = Jsoup.connect(surl).get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Elements companyName = doc.select("h1.ts-h-company-mainTitle");
//		String comTitle = companyName.text();
//		Elements element = doc.select("div.ts-h-company-media02-main");
//		String elStr = element.text();
//		
//		System.out.println("회사이름: " + comTitle);
//		System.out.println("인물상: " + elStr);
//		
//	}
	//번역 메인 메서드
	public static void main(String[] args) {

		String cc = "r723110041";
		String elStr;
		// 파파고 번역
		String clientId = "9VLCjOECDMTITU4FwWNG";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "nNz9z7RPL_";// 애플리케이션 클라이언트 시크릿값";
		try {
			elStr = getResponse(cc);
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

			String resultText = response.toString();
			Charset.forName("UTF-8").encode(resultText);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(resultText);
			JSONObject message = (JSONObject) obj.get("message");

			JSONObject res = (JSONObject) message.get("result");

			String translatedText = (String) res.get("translatedText");
			
			maTest(translatedText);
			keTest(translatedText);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//형태소 분석
	public static void maTest(String str) {
		try {
			MorphemeAnalyzer ma = new MorphemeAnalyzer();
			ma.createLogger(null);
			Timer timer = new Timer();
			timer.start();
			List<MExpression> ret = ma.analyze(str);
			timer.stop();
			timer.printMsg("Time");
			ret = ma.postProcess(ret);

			ret = ma.leaveJustBest(ret);

			List<Sentence> stl = ma.divideToSentences(ret);
			for (int i = 0; i < stl.size(); i++) {
				Sentence st = stl.get(i);
				System.out.println("=============================================  " + st.getSentence());
				for (int j = 0; j < st.size(); j++) {
					System.out.println(st.get(j));
				}
			}
//			ma.closeLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void keTest(String str) {
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(str, true);
		for( int i = 0; i < kl.size(); i++ ) {
			Keyword kwrd = kl.get(i);
			System.out.println(kwrd.getString() + "\t" + kwrd.getCnt());
		}
	}
}
