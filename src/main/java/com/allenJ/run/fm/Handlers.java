package com.allenJ.run.fm;

import com.allenJ.run.fm.handler.EchoHandler;
import com.allenJ.run.fm.handler.Handler;
import com.allenJ.run.fm.handler.HelloHandler;
import com.allenJ.run.fm.handler.WorldHandler;


public class Handlers {
	
	public static Handler create(Request req){
		String path = req.getPath();
		if("hello".equalsIgnoreCase(path)){
			return new HelloHandler();
		}
		if("world".equalsIgnoreCase(path)){
			return new WorldHandler();
		}
		return new EchoHandler();
	}
}
