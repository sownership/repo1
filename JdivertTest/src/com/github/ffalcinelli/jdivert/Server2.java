package com.github.ffalcinelli.jdivert;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static void main(String[] args) throws Exception {

		try (ServerSocket ss = new ServerSocket(8011)) {
			while (true) {
				Socket s = ss.accept();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try (Socket c = s; BufferedInputStream bis = new BufferedInputStream(c.getInputStream())) {
							byte[] b = new byte[1024 * 8];
							int len;
							while ((len = bis.read(b)) != -1) {
								System.out.println(new String(b));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
	}
}
