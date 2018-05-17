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
				Iterator<SelectionKey> keyIt = selector.selectedKeys().iterator();
				while(keyIt.hasNext()){
					SelectionKey key = keyIt.next();
					keyIt.remove();
					System.out.println(key);
					try{
						handleSelectionKey(key, selector);
					}catch(IOException e){
						e.printStackTrace();
						try{
							System.out.println("close channel");
							key.channel().close();
							key.cancel();
						}catch(Exception ex){
							//ignore
						}
					}
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
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel channel = server.accept();
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
			return;
		}
		if(key.isConnectable()){
			System.out.println("A client connecting");
			key.channel().register(selector, SelectionKey.OP_READ);
			return;
		}
		if(key.isReadable()){
			System.out.println("readable event");
			StringBuilder sb = new StringBuilder("from clien: ");
			SocketChannel channel = (SocketChannel) key.channel();
			if(!readFromClient(sb, channel)){
				System.out.println("remote client closed 2");
				key.cancel();
				channel.close();
				return;
			}
			System.out.println(sb.toString());
			ByteBuffer wBuffer = ByteBuffer.allocate(256);
			wBuffer.put(("from server: hi, " + sb.append(";").toString()).getBytes());
			wBuffer.flip();
			channel.write(wBuffer);
			return;
		}
		if(key.isWritable()){
			
		}
	}

	private boolean readFromClient(StringBuilder sb, SocketChannel channel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(2);
        int readBytes = channel.read(buffer);
        if(readBytes == -1){
        	return false;
        }
		while(readBytes > 0){
			buffer.flip();
			while(buffer.hasRemaining()){
				sb.append((char)buffer.get());
			}
			buffer.clear();
			readBytes = channel.read(buffer);
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			new NioServer().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
