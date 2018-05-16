package com.allenJ.run.fm.handler;

import com.allenJ.run.fm.Context;
import com.allenJ.run.fm.Request;
import com.allenJ.run.fm.Response;
import com.allenJ.run.fm.annotaion.AutoInject;

public class WorldHandler implements Handler {

	@AutoInject
	private Context context;
	
	@Override
	public void handle(Request request, Response response) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.send(Thread.currentThread().getName() + ":: message from WorldHandler: " + context.getAttr("idx"));
	}

}
