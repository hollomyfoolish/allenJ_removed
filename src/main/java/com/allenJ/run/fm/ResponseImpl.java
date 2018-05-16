package com.allenJ.run.fm;

public class ResponseImpl implements Response {

	@Override
	public void send(String message) {
		System.out.println(message);
	}

}
