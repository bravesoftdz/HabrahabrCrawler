package net.habrahabr;

import java.io.IOException;
import net.habrahabr.prog.ParseSite;
import net.habrahabr.prog.Printer;

public class Application {

	public void run() throws IOException {
		String link = "http://habrahabr.ru/hubs/";
		ParseSite parseSite = new ParseSite();
		parseSite.setLink(link);
		parseSite.parseCategory();
		parseSite.parseHub();
		//Printer.printHubCategory(parseSite.getCategories());
	}

	

}
