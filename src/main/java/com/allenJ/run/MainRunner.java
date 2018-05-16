package com.allenJ.run;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.allenJ.run.fm.ClienRequestDispaterTask;
import com.allenJ.run.fm.ContextImpl;
import com.allenJ.run.fm.Request;
import com.allenJ.run.fm.RequestImpl;
import com.allenJ.run.fm.Response;
import com.allenJ.run.fm.ResponseImpl;

public class MainRunner {

	public static void main(String[] args) {
		List<String> paths = new ArrayList<String>();
		paths.add("hello");
		paths.add("world");
		paths.add("nopath");
		paths.add("nopath2");
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		ContextImpl context = new ContextImpl();
		for(int i = 0; i < 100; i++){
//			String path = paths.get(new Random().nextInt(paths.size()));
			String path = paths.get(i % paths.size());
			Request reqeust = new RequestImpl(path);
			Response response = new ResponseImpl();
			executorService.submit(new ClienRequestDispaterTask(i, context, reqeust, response));
		}
		executorService.shutdown();
	}

}
