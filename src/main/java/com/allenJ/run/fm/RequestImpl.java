package com.allenJ.run.fm;

public class RequestImpl implements Request {

	private String path;

	public RequestImpl(String path){
		this.path = path;
	}
	
	@Override
	public String getPath() {
		return this.path;
	}

}
