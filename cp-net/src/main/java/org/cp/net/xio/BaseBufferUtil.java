package org.cp.net.xio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

public class BaseBufferUtil {

	static ByteBuffer tempByteBuffer = ByteBuffer.allocate(1);

	public byte[] get(ReadableByteChannel readableByteChannel, int len) throws IOException {
		byte[] a = new byte[len];
		tempByteBuffer.clear();
		for (int i = 0; i < len; i++) {
			readableByteChannel.read(tempByteBuffer);
			tempByteBuffer.flip();
			a[i] = tempByteBuffer.get();
			tempByteBuffer.clear();
		}
		return a;
	}

	public byte[] get(ByteBuffer buff, int len) {
		byte[] a = new byte[len];
		for (int i = 0; i < len; i++) {
			a[i] = buff.get();
		}
		return a;
	}

	public ByteBuffer get(byte[] msg, int len) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(msg.length + len);
		byteBuffer.put(String.format("%0" + len + "d", msg.length).getBytes()).put(msg).flip();
		return byteBuffer;
	}

	public static void main(String[] args) throws SocketException, UnknownHostException {
		byte[] b = "1000".getBytes();
		System.out.println(String.format("%0" + 8 + "d", 1000));

		byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
		for (int i = 0; i < mac.length; i++) {
			int temp = mac[i] & 0xff;
			// String str = Integer.toHexString(mac[i]);
			String str = Integer.toHexString(temp);
			System.out.println(str);
		}
		System.out.println(mac.length);
	}
}
