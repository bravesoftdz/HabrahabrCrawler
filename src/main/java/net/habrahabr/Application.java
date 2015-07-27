package net.habrahabr;

import java.io.IOException;
import net.habrahabr.prog.ParseSite;
import net.habrahabr.prog.util.Printer;

public class Application {

	String link = "http://habrahabr.ru/hubs/";
	
	public void run() throws IOException {
		
		ParseSite parseSite = new ParseSite();
		parseSite.setLink(link);
		parseSite.parseCategory();
		parseSite.parseHub();
		//parseSite.saveHubs();
		Printer.printHubCategory(parseSite.getHubs());
	}

	

}
