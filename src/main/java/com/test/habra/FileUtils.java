package com.test.habra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

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

}
