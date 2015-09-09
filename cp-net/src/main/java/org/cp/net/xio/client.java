package org.cp.net.xio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Date now = new Date();
		BaseBufferUtil bu = new BaseBufferUtil();
		Socket socket = new Socket("127.0.0.1", 8300);
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		out.write(bu.get("客户端发送".getBytes(), 8).array());
		out.flush();
		byte[] lenB = new byte[8];
		in.read(lenB);
		int len = Integer.valueOf(new String(lenB));
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		while (len-- > 0) {
			o.write(in.read());
		}
		System.out.println(new String(o.toByteArray()));
		System.out.println(new Date().getTime() - now.getTime());
	}

}
