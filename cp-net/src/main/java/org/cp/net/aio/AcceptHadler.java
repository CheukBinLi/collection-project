package org.cp.net.aio;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHadler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

	public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
		try {
			// 接受连接
			attachment.accept(attachment, this);
			//
			System.err.println("有客到：" + result.getRemoteAddress().toString());
//			result.read
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {

	}

}
