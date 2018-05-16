package com.allenJ.run.fm;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalService {
	private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();
	
	public static Map<String, Object> get(boolean createIfNull){
		Map<String, Object> attrs = threadLocal.get();
		if(attrs == null && createIfNull){
			attrs = new HashMap<String, Object>();
			threadLocal.set(attrs);
		}
		return attrs;
	}
	
	public static void clear(){
		threadLocal.remove();
	}
}
