package net.habrahabr.prog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.habrahabr.prog.exception.EmptyCategoryException;
import net.habrahabr.prog.model.Category;
import net.habrahabr.prog.model.Post;
import net.habrahabr.prog.util.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteParser {

	private String link;
	private List<Category> categories = new ArrayList<>();
	private Map<String, List<Category>> hubs = new LinkedHashMap<>();
	private List<Post> posts;

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
		Document document = Jsoup.connect(link).timeout(100000).get();
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
	
	private void parseHubPage(String link, List<Category> list) throws IOException {
		Document document = Jsoup.connect(link).timeout(100000).get();
		
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
			parseHubPage("http://habrahabr.ru" + nextPage.first().attr("href"), list);
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
			parseHubPage(link, list);
			hubs.put(name, list);
		}
	}
	
	public void parsePosts() throws IOException {
		String name = "HabraHabr";
		File folder = new File(name);
		folder.mkdir();
		for (String key : hubs.keySet()) {
			folder = new File(name + '/' + key);
			folder.mkdirs();
			List<Category> list = hubs.get(key);
			System.out.println("Category : " + key + " start parsing");
			for (int k = 0; k < list.size(); k++) {
				String catName = list.get(k).getName();
				// 2 lines below needed only for Windows, since OS can not create folders with symbol '/' and '*'
				//catName = catName.replaceAll("/", "_");
				//catName = catName.replaceAll("/*", "_");
				String link = list.get(k).getLink();
				folder = new File(name + '/' + key + '/' + catName);
				folder.mkdirs();
				posts = new LinkedList<Post>();
				parsePostsPage(link, posts);
				File catFile = new File(folder.getCanonicalPath() + "/" + catName + ".txt");
				catFile.createNewFile();
				FileUtils.writeToFile(catFile, posts);
				System.out.println("   Sub Category : " + catName + " parsing is finished");
			}
			System.out.println("Category : " + key + " parsing is finished");
		}
	}
	
	private void parsePostsPage(String link, List<Post> postsList) throws IOException {
		Document document = Jsoup.connect(link).timeout(100000).get();
		Elements posts = document.select(".post_title");
		for(Element post : posts) {
			Elements element = post.getElementsByTag("a");
			Post shortPost = new Post();
			shortPost.setName(element.text());
			shortPost.setLink(element.get(0).attr("href"));
			postsList.add(shortPost);
			
		}
		Elements nextPage = document.select(".next");
		if(!nextPage.isEmpty()) {
			String nextLink = "http://habrahabr.ru" + nextPage.first().attr("href");
			parsePostsPage(nextLink, postsList);
		}
	}
}
