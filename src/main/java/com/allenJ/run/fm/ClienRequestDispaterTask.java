package com.allenJ.run.fm;

import java.lang.reflect.Field;

import com.allenJ.run.fm.annotaion.AutoInject;
import com.allenJ.run.fm.handler.Handler;

public class ClienRequestDispaterTask implements Runnable{

	private Request request;
	private Response response;
	private ContextImpl context;
	private int idx;

	public ClienRequestDispaterTask(int idx, ContextImpl context, Request request, Response response){
		this.request = request;
		this.response = response;
		this.context = context;
		this.idx = idx;
	}

	@Override
	public void run() {
		Handler handler = getHandler(request);
		this.context.addAttr("idx", this.idx);
		handler.handle(request, response);
	}

	private Handler getHandler(Request req) {
		Handler handler = Handlers.create(req);
		Field[] fields = handler.getClass().getDeclaredFields();
		for(Field f : fields){
			if(f.isAnnotationPresent(AutoInject.class) && f.getType() == Context.class){
				f.setAccessible(true);
				try {
					f.set(handler, this.context);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return handler;
	}
}
