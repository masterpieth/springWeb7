package com.sesoc.web7.util;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {

	public static void main(String[] args) {
		String crawlStorageFolder = "data/crawler";
		int numberOfCrawlers = 1;
		
		CrawlConfig config = new CrawlConfig();
		
		config.setMaxDepthOfCrawling(1);
		config.setPolitenessDelay(500);
		config.setCrawlStorageFolder(crawlStorageFolder);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = null;
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
		} catch (Exception e) {
		}
		controller.addSeed("https://job.rikunabi.com/2021/s/?fw=IT&isc=r21rcnz00945");
		
		controller.start(MyCrawler.class, numberOfCrawlers);
	}
}
