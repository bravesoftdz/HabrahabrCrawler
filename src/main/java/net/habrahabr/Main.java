package net.habrahabr;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {		
		try {
			long start = System.currentTimeMillis();
			new Application().run();
			long end = System.currentTimeMillis();
			System.out.println("Time for processing " + (end - start));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
