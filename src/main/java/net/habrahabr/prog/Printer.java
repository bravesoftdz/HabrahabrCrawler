package net.habrahabr.prog;

import java.util.List;

public class Printer {
	public static void printHubCategory(List<Category> hubs) {
		for (int k = 0; k < hubs.size(); k++) {
			System.out.println(hubs.get(k));
		}
	}
}
