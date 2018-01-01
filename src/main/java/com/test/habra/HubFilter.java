package com.test.habra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class HubFilter {
	public Set<String> loadHubFilter(String fileName) throws IOException, URISyntaxException {
		Set<String> filter = new HashSet<>();
		File file = new File(fileName);
		if (file.exists()) {
			URI uri = this.getClass().getResource("\filter.txt").toURI();
			Files.readAllLines(Paths.get(uri)).forEach(System.out::println);
			
//			try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
//				String line = reader.readLine();
//				filter.add(line);
//				System.out.println(line);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			return filter;
		} else {
			return filter;
		}
		
	}
}
