package com.rekoe.mvc.config;

import org.nutz.lang.util.Context;

public class FilterGameConfig extends AbstractGameConfig{

	@Override
	public String getAppRoot() {
		return null;
	}

	@Override
	public String getAppName() {
		return null;
	}

	@Override
	public Context getLoadingContext() {
		return GameMvcs.getActionContext();
	}

	@Override
	public void setAttribute(String name, Object obj) {
		GameMvcs.setAttribute(name, obj);
	}
	@Override
	public Object getAttribute(String name){
		return GameMvcs.getAttribute(name);
	}
}
