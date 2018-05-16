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
				out.write(i.getBytes());
				out.flush();
				if(i.endsWith(";")){
					readFromServer(in);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFromServer(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		char c;
		while((c = (char)in.read()) != ';'){
			sb.append(c);
		}
		System.out.println("from server: " + sb.toString());
	}

}
