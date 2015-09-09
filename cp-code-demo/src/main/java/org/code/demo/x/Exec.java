package org.code.demo.x;

import java.io.IOException;

public class Exec {

	public static void main(String[] args) throws IOException,
			InterruptedException {

		Runtime r = Runtime.getRuntime();
		Process p = r
				.exec("C:/Program Files (x86)/Internet Explorer/iexplore.exe C:/Users/Administrator/Desktop/加载遮罩.html");
		// Process p =
		// r.exec("C:/Program Files (x86)/LuDaShi/ComputerZ_CN.exe");
		Thread.sleep(10000);
		p.destroy();
	}

}
