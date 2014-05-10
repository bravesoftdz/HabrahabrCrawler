package net.habrahabr.prog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseSite {
	private String link;
	private List<Category> categories = new ArrayList<>();
	private Map<String, List<Hub>> hubs = new LinkedHashMap<>();

	public void setLink(String link) {
		this.link = link;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void parseCategory() throws IOException {
		if(link == null) {
			throw new NullPointerException("empty link");
		}
		Document document = Jsoup.connect(link).get();
		Elements category = document.select(".categories");
		Elements list = category.select("li");

		boolean allHubs = true;

		for (Element element : list) {
			if (allHubs) {
				allHubs = false;
				continue;
			}
			Category hub = new Category();
			int amount = Integer.valueOf(element.getElementsByClass("counter")
					.text().replaceAll("[()]", ""));
			hub.setAmount(amount);

			Elements links = element.getElementsByTag("a");
			Element link = links.first();
			hub.setLink("http://habrahabr.ru" + link.attr("href"));
			hub.setName(link.text());
			categories.add(hub);
		}
	}
	
	private void parsePage(String link, List<Hub> list) throws IOException {
		Document document = Jsoup.connect(link).get();
		
		Elements hubs = document.select(".info");
		for(Element hubElement : hubs) {
			Hub hub = new Hub();
			Elements links = hubElement.getElementsByTag("a");
			hub.setName(links.get(0).text());
			hub.setSubscribers(Integer.valueOf((links.get(1).text().split(" ")[0])));
			hub.setLink(links.get(2).attr("href"));
			hub.setPostAmount(Integer.valueOf(links.get(2).text().split(" ")[0]));
			list.add(hub);
		}
		
		Elements nextPage = document.select(".next");
		if(!nextPage.isEmpty()) {
			parsePage("http://habrahabr.ru" + nextPage.first().attr("href"), list);
		}
	}
	public void parseHub() throws IOException {
		if(categories.size() == 0) {
			throw new EmptyCategoryException("empty categories");
		}
		for(Category category : categories) {
			String link = category.getLink();
			String name = category.getName();
			List<Hub> list = new LinkedList<>();
			parsePage(link, list);
			hubs.put(name, list);
		}
		System.out.println(hubs);
	}
	
	public void saveHubs() {
		FileUtils.save(hubs);
	}
}
