package org.cp.net.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

	private ExecutorService executor = Executors.newCachedThreadPool();

	private AsynchronousChannelGroup asynchronousChannelGroup;
	private AsynchronousServerSocketChannel serverSocketChannel;

	public server() throws IOException {
		asynchronousChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);
		// 重用端口->绑定端口
		serverSocketChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup).setOption(StandardSocketOptions.SO_REUSEADDR, true).bind(new InetSocketAddress(10086));

	}

}
