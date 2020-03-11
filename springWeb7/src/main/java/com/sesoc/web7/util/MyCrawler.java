package com.sesoc.web7.util;

import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler{

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");
	
	@Override
	public boolean shouldVisit(Page refferingPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		
		return !FILTERS.matcher(href).matches()
				&& href.startsWith("https://job.rikunabi.com/");
	}
	
	@Override
	public void visit(Page page) {
		
		if(page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());
            
            Document doc = Jsoup.parse(html);
            
            String content = doc.select("div.ts-h-company-media02-main").html();
            logger.debug("div.ts-h-company-media02-main : {}", content);
            
		}
	}
}
