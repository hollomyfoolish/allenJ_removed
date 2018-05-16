package com.allenJ.run.fm.handler;

import com.allenJ.run.fm.Request;
import com.allenJ.run.fm.Response;

public interface Handler {

	void handle(Request request, Response response);

}
