package com.allenJ.run.fm.handler;

import com.allenJ.run.fm.Context;
import com.allenJ.run.fm.Request;
import com.allenJ.run.fm.Response;
import com.allenJ.run.fm.annotaion.AutoInject;

public class EchoHandler implements Handler {

	@AutoInject
	private Context context;
	
	@Override
	public void handle(Request request, Response response) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.send(Thread.currentThread().getName() + ":: message from EchoHandler: " + context.getAttr("idx"));
	}

}
