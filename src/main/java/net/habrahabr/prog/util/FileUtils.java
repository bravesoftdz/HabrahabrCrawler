package net.habrahabr.prog.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import net.habrahabr.prog.model.Category;
import net.habrahabr.prog.model.Post;

public class FileUtils {
	
	public static void writeToFile(File file, List<Post> posts) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		
		for(int i = 0; i < posts.size(); i++) {
			bw.write(posts.get(i).getName() + " | " + posts.get(i).getLink());
			bw.newLine();
			bw.newLine();
		}
		
		bw.close();
	}
}
