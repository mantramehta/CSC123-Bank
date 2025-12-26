package com.usman.csudh.bank.net;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.usman.csudh.bank.MainBank;

public class NetworkListener {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(80);
		try {
			while (true) {
				Socket sock = server.accept();
				try {
					banner(sock.getOutputStream());
					new MainBank(sock.getInputStream(), sock.getOutputStream()).run();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						sock.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} finally {
			server.close();
		}
	}
	
	private static void banner(OutputStream out) throws IOException {
		out.write("\n\nWelcome to the Bank\n".getBytes());
		out.flush();
	}

}
