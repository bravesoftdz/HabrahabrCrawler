package com.test.habra;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteCrawler {

	private final String url;
	private final Integer TIMEOUT = 10_000;
	private List<Hub> hubs = new LinkedList<>();
	private BlockingQueue<Map<String, List<Post>>> queue;
	private AtomicBoolean flag;
	
//	{
//		hubs.add(new Hub("Git", "https://habrahabr.ru/hub/git/"));
//	}

	public SiteCrawler(String url, BlockingQueue<Map<String, List<Post>>> queue, AtomicBoolean flag) {
		this.url = url;
		this.queue = queue;
		this.flag = flag;
	}
	
	public void parse() throws IOException, InterruptedException {
		System.out.println("Prepare hubs list.");
		
		prepareHubs(url);
		
		System.out.println("Hubs list preparation completed.");
		System.out.println("Found " + hubs.size() + " hubs");
		System.out.println("Start parsing hubs list, quantity : " + hubs.size());
		
		parsePreparedHubs();
		
		System.out.println("Completed parsing hubs list.");
	}

	private void parsePreparedHubs() throws IOException, InterruptedException {
		System.out.println("Completed 0 %");
		float completed = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		
		for (Hub hub : hubs) {
			String hubName = hub.getName();
			List<Post> postList = new LinkedList<>();
			Map<String, List<Post>> posts = new LinkedHashMap<>();
			posts.put(hub.getName(), parseSubHubPage(hubName, hub.getUrl(), postList));
			queue.put(posts);
			System.out.println("Completed " + df.format((100 * completed) / this.hubs.size()) + " %");
			completed++;
		}
		
		System.out.println("Completed all.");
		flag.set(false);
	}

	private List<Post> parseSubHubPage(String hubName, String url, List<Post> postList) throws IOException {
		Document page = Jsoup.connect(url).timeout(TIMEOUT).get();
		Elements posts = page.getElementsByClass("post__title_link");

		posts.forEach(postElement -> postList.add(new Post(postElement.text(), postElement.absUrl("href"))));

		Element nextPageElement = page.getElementById("next_page");
		if (Objects.nonNull(nextPageElement)) {
			parseSubHubPage(hubName, nextPageElement.absUrl("href"), postList);
		}

		return postList;
	}

	private void prepareHubs(String url) throws IOException {
		Document page = Jsoup.connect(url).timeout(TIMEOUT).get();
		Elements pageElements = page.getElementsByClass("media-obj__body");

		pageElements.stream().map(element -> element.getElementsByClass("list-snippet__title-link"))
							 .forEach(data -> hubs.add(new Hub(data.get(0).text(), data.get(0).absUrl("href"))));
		
		Element nextPageElement = page.getElementById("next_page");
		if (Objects.nonNull(nextPageElement)) {
			prepareHubs(nextPageElement.absUrl("href"));
		}
	}
}
