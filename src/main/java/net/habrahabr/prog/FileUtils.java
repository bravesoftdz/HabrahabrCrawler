package net.habrahabr.prog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class FileUtils {
	private static final String fileName = "hub.dat";

	public static void save(Map<String, List<Hub>> hubs) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(fileName))) {
			if (oos != null) {
				oos.writeObject(hubs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, List<Hub>> load() {
		Map<String, List<Hub>> hubs = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				fileName))) {
			if (ois != null) {
				hubs = (Map<String, List<Hub>>) ois.readObject();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return hubs;
	}
}
