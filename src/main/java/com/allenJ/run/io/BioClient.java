package com.allenJ.run.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {

	public static void main(String[] args) {
		try (
			Socket client = new Socket();
		){
			client.connect(new InetSocketAddress("localhost", 9527));
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			for(String i = userInput.readLine(); i != null; i = userInput.readLine()){
				System.out.println("user input: " + i);
				if("quit".equalsIgnoreCase(i)){
					in.close();
					out.close();
					client.close();
					System.out.println("end connetion");
					return;
				}
				out.write(i.getBytes());
				out.flush();
				if(i.endsWith(";")){
					if(!readFromServer(in)){
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean readFromServer(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c = in.read();
		if(c == -1){
			return false;
		}
		while((char)c != ';'){
			sb.append((char)c);
			c = in.read();
		}
		System.out.println("from server: " + sb.toString());
		return true;
	}

}
