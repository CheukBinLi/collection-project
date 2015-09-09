package org.code.demo.ftp;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FtpClientTest {

	public void downd() throws IOException {

		FTPClient ftpClient = new FTPClient();
		ftpClient.login("ftp1", "ftp1");
		ftpClient.connect("");
		String downPath = "";
		String savePath = "";
		ftpClient.setBufferSize(1024);
		FileOutputStream fos = new FileOutputStream(savePath);
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.retrieveFile(downPath, fos);
		// new simple
	}
}
