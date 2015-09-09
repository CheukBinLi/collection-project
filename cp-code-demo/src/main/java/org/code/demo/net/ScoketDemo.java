package org.code.demo.net;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ScoketDemo {

	private static Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException {
		DataOutputStream out;
		DataInputStream in;
		socket = new Socket("127.0.0.1", 10086);
		int len = 0;
		byte[] buffer = new byte[512];
		ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		// out.writeUTF("aaaa");
		ByteArrayOutputStream outx = new ByteArrayOutputStream();
		outx.write(1999999992);
		out.write(outx.toByteArray());

		out.flush();
		System.out.println(in.readUTF());
		out.close();
		in.close();
		socket.close();
	}

	public static byte[] intToByteArray1(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}
}
