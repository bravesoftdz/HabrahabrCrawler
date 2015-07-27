package net.habrahabr;

import java.io.IOException;
import net.habrahabr.prog.SiteParser;
import net.habrahabr.prog.util.Printer;

public class Application {

	String link = "http://habrahabr.ru/hubs/";
	
	public void run() throws IOException {
		
		SiteParser parseSite = new SiteParser();
		parseSite.setLink(link);
		parseSite.parseCategory();
		parseSite.parseHub();
		parseSite.parsePosts();
		//parseSite.saveHubs();
		//Printer.printHubCategory(parseSite.getHubs());
	}

	

}
