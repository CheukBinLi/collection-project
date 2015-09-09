package org.cp.net.xio;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DoQueue implements Runnable {

	static Set<Object> queue = new HashSet<Object>();
	static BlockingQueue<Object> clearQueue = new LinkedBlockingQueue<Object>();
	static DoQueue newInstance = new DoQueue();
	static Thread listener;

	protected DoQueue() {
		super();
	}

	public static void start() {
		if (null == listener || listener.isInterrupted())
			listener = new Thread(newInstance);
		listener.start();
	}

	public static void stop() {
		if (null != listener)
			listener.interrupt();
		listener = null;
	}

	public static void doWork(Object o) {
		queue.add(o);
	}

	public static boolean contains(Object o) {
		return queue.contains(o);
	}

	public static void doFinish(Object o) {
		try {
			clearQueue.put(o);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (!Thread.interrupted()) {
			try {
				queue.remove(clearQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
