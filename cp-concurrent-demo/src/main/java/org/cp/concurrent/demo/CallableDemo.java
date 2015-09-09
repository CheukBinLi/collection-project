package org.cp.concurrent.demo;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableDemo implements Callable<String> {

	public String call() throws Exception {
		int i = 0;
		Date now = new Date();
		StringBuffer sb = new StringBuffer();
		while (i++ < 10000) {
			sb.append((char) i);
//			Thread.sleep(100);
		}
		System.err.println("共花费：" + (new Date().getTime() - now.getTime()) + "毫秒");
		return sb.toString();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future<String> future = Executors.newCachedThreadPool().submit(new CallableDemo());
		System.out.println(future.get());
	}
}
