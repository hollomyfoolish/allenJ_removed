package com.allenJ.run.fm;

import java.util.Map;


public class ContextImpl implements Context {

	public void addAttr(String key, Integer idx) {
		ThreadLocalService.get(true).put(key, idx);
	}

	@Override
	public Object getAttr(String key) {
		Map<String, Object> c = ThreadLocalService.get(false);
		if(c == null){
			return null;
		}
		return c.get(key);
	}
}
