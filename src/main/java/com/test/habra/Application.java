package com.test.habra;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
	
	void run(String ... args) throws IOException, InterruptedException, URISyntaxException {
		String url = args[0];
		new HubFilter().loadHubFilter("filter.txt").forEach(System.out::println);
//		FileUtils.loadLastHubPost();
//		BlockingQueue<Map<String, List<Post>>> queue = new LinkedBlockingDeque<>();
//		AtomicBoolean flag = new AtomicBoolean(true);
//		
//		PostStorage filePostStorage = new PostStorage(queue, flag);
//		new Thread(filePostStorage).start();
//		
//		SiteCrawler habra = new SiteCrawler(url, queue, flag);
//		habra.parse();
	}
}
