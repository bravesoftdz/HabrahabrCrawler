package com.test.habra;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
	
	void run(String ... args) throws IOException, InterruptedException {
		String url = args[0];
		
		BlockingQueue<Map<String, List<Post>>> queue = new LinkedBlockingDeque<>();
		AtomicBoolean flag = new AtomicBoolean(true);
		
		PostStorage filePostStorage = new PostStorage(queue, flag);
		new Thread(filePostStorage).start();
		
		HabraSiteParser habra = new HabraSiteParser(url, queue, flag);
		habra.parse();
	}
}
