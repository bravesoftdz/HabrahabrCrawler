package com.test.habra;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostStorage implements Runnable {

	private BlockingQueue<Map<String, List<Post>>> queue;
	private AtomicBoolean flag;
	
	public PostStorage(BlockingQueue<Map<String, List<Post>>> queue, AtomicBoolean flag) {
		this.queue = queue;
		this.flag = flag;
	}

	@Override
	public void run() {
		while(flag.get()) {
			try {
				FileUtils.save(queue.take());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}

}
