package com.allenJ.run.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

	public void start() throws IOException{
		ServerSocketChannel server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(InetAddress.getByName("localhost"), 9527));
		server.configureBlocking(false);
		Selector selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("server started");
		while(true){
			int keySize = selector.select();
			if(keySize > 0){
				Iterator<SelectionKey> keyIt = selector.keys().iterator();
				while(keyIt.hasNext()){
					SelectionKey key = keyIt.next();
					System.out.println(key);
					handleSelectionKey(key, selector);
				}
			}
		}
		
//		try(
//				ServerSocket server = new ServerSocket();
//			){
//				server.bind(new InetSocketAddress("127.0.0.1", 9527));
//				System.out.println("server start");
//				while(true){
//					Socket client = server.accept();
//					System.out.println("client received: " + client.getInetAddress() + ":" + client.getPort());
//				}
//			} catch (IOException e) {
//				throw e;
//			}
		}
	
	private void handleSelectionKey(SelectionKey key, Selector selector) throws IOException {
		if(!key.isValid()){
			System.out.println("remote client closed 1");
			key.cancel();
			key.channel().close();
			return;
		}
		if(key.isAcceptable()){
			System.out.println("A client connecting");
			key.channel().register(selector, SelectionKey.OP_CONNECT);
			return;
		}
		if(key.isConnectable()){
			System.out.println("A client connecting");
			key.channel().register(selector, SelectionKey.OP_READ);
			return;
		}
		if(key.isReadable()){
			ByteBuffer buffer = ByteBuffer.allocate(256);
			SocketChannel channel = (SocketChannel) key.channel();
			int readBytes = channel.read(buffer);
			if(readBytes == -1){
				System.out.println("remote client closed 2");
				key.cancel();
				channel.close();
				return;
			}
			if(readBytes > 0){
				buffer.flip();
				StringBuilder sb = new StringBuilder("from clien: ");
				while(buffer.hasRemaining()){
					sb.append((char)buffer.get());
				}
				buffer.clear();
				buffer.put("from server: hi".getBytes());
				channel.write(buffer);
			}
			return;
		}
		if(key.isWritable()){
			
		}
	}

	public static void main(String[] args) {
		try {
			new NioServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
