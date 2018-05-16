package com.allenJ.run.fm.handler;

import com.allenJ.run.fm.Request;
import com.allenJ.run.fm.Response;

public class HelloHandler implements Handler {

	@Override
	public void handle(Request request, Response response) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.send(Thread.currentThread().getName() + ":: message from HelloHandler");
	}

}
