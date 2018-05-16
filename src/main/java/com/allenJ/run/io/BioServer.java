package com.allenJ.run.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BioServer {
	private ExecutorService executor;

	public BioServer(){
		this.executor = new ThreadPoolExecutor(10, 100, 5L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
	}
	
	public void start() throws IOException{
		try(
			ServerSocket server = new ServerSocket();
		){
			server.bind(new InetSocketAddress("127.0.0.1", 9527));
			System.out.println("server start");
			while(true){
				Socket client = server.accept();
				System.out.println("client received: " + client.getInetAddress() + ":" + client.getPort());
				handleClient(client);
			}
		} catch (IOException e) {
			throw e;
		}
	}

	private void handleClient(final Socket client) {
		this.executor.submit(new Runnable(){
			@Override
			public void run() {
				try {
					InputStream in = client.getInputStream();
					OutputStream out = client.getOutputStream();
					StringBuilder sb = new StringBuilder();
					char c;
					while(true && !client.isClosed()){
						while((c = (char)in.read()) != ';'){
							sb.append(c);
						}
						sb.append(";");
						out.write(sb.toString().getBytes());
						out.flush();
						sb = new StringBuilder();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public static void main(String[] args){
		try {
			new BioServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
