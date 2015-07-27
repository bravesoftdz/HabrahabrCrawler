package net.habrahabr.prog.util;

import java.util.List;
import java.util.Map;

import net.habrahabr.prog.model.Category;

public class Printer {
	public static void printHubCategory(Map<String, List<Category>> hubs) {
		int sum = 0;
		for (String key : hubs.keySet()) {
			List<Category> list = hubs.get(key);
			sum += list.size();
			for (int k = 0; k < list.size(); k++) {
				System.out.println(list.get(k));
			}
		}
		System.out.println(sum);
	}
}
