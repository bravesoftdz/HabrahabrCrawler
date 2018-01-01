package com.test.habra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileUtils {

	public static void writeToFile(File file, List<Post> posts) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		for (Post post : posts) {
			bw.write(post.getName() + " | " + post.getLink());
			bw.newLine();
			bw.newLine();
		}

		bw.close();
	}

	public static void save(Map<String, List<Post>> hubs) throws IOException {
		File folder = new File("Habrahabr");
		if (!folder.exists()) {
			folder.mkdir();
		}
		for (String key : hubs.keySet()) {
			List<Post> posts = hubs.get(key);
			String fileName = key.replaceAll("[\\*/]", "_");
			File file = new File(folder.getCanonicalPath() + "/" + fileName + ".txt");
			file.createNewFile();
			writeToFile(file, posts);
		}
	}
	
	public static Set<String> loadLastHubPost() {
		Set<String> hubRecords = new HashSet<>();
		File folder = new File("Habrahabr");
		if (folder.exists()) {
			hubRecords =  Arrays.stream(folder.listFiles()).map(file -> {
				String line = null;
				try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
					line = reader.readLine();
					return line.substring(0, line.indexOf(" |"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return line;
			}).collect(Collectors.toSet());
			//hubRecords.stream().forEach(System.out::println);
			return hubRecords;
		} else {
			return hubRecords;
		}
	}

}
