package net.habrahabr.prog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.habrahabr.prog.exception.EmptyCategoryException;
import net.habrahabr.prog.model.Category;
import net.habrahabr.prog.util.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseSite {
	private String link;
	private List<Category> categories = new ArrayList<>();
	private Map<String, List<Category>> hubs = new LinkedHashMap<>();

	public void setLink(String link) {
		this.link = link;
	}

	public List<Category> getCategories() {
		return categories;
	}
	
	public Map<String, List<Category>> getHubs() {
		return hubs;
	}

	public void parseCategory() throws IOException {
		if(link == null) {
			throw new NullPointerException("blank link");
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
			Category cat = new Category();
			Elements links = element.getElementsByTag("a");
			Element link = links.first();
			cat.setLink("http://habrahabr.ru" + link.attr("href"));
			cat.setName(link.text());
			categories.add(cat);
		}
	}
	
	private void parsePage(String link, List<Category> list) throws IOException {
		Document document = Jsoup.connect(link).get();
		
		Elements hubs = document.select(".info");

		for(Element hubElement : hubs) {
			Category hub = new Category();
			Elements links = hubElement.getElementsByTag("a");
			hub.setName(links.get(0).text());
			hub.setLink(links.get(0).attr("href") + "all/");
			list.add(hub);
		}
		
		Elements nextPage = document.select(".next");
		if(!nextPage.isEmpty()) {
			parsePage("http://habrahabr.ru" + nextPage.first().attr("href"), list);
		}
	}
	public void parseHub() throws IOException {
		if(categories.size() == 0) {
			throw new EmptyCategoryException("blank categories");
		}
		for(Category category : categories) {
			String link = category.getLink();
			String name = category.getName();
			List<Category> list = new LinkedList<>();
			parsePage(link, list);
			hubs.put(name, list);
		}
		System.out.println(hubs);
	}
	
	public void saveHubs() {
		FileUtils.save(hubs);
	}
}
