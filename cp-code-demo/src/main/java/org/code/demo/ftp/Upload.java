package org.code.demo.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Upload {

	private URLConnection urlConnection;
	private ByteArrayOutputStream out;
	private RandomAccessFile raf;
	private OutputStream os;
	private File file;

	public boolean upload(File file, URL url) {
		try {
			raf = new RandomAccessFile(file, "r");
			out = new ByteArrayOutputStream();
			byte[] b = new byte[512];
			int len = 0;
			while ((len = raf.read(b)) > 0) {
				out.write(b, 0, len);
			}
			urlConnection = url.openConnection();
			OutputStream os = urlConnection.getOutputStream();
			os.write(out.toByteArray());
			os.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != os)
				try {
					os.close();
				} catch (IOException e) {
				}
			if (null != os)
				try {
					out.close();
				} catch (IOException e) {
				}
			if (null != os)
				try {
					raf.close();
				} catch (IOException e) {
				}
		}
	}

	public static void main(String[] args) throws MalformedURLException {
		// File f = new File("C:/Users/Administrator/Desktop/boomhope-tsbank-web.war");
		File f = new File("C:/Users/Administrator/Desktop/MQ生成Maven库.txt");
		new Upload().upload(f, new URL("ftp://127.0.0.1/a.txt"));
	}
}
