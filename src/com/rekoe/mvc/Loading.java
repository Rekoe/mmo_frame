package com.rekoe.mvc;

import com.rekoe.mvc.config.GameConfig;


public interface Loading {

	public static final String CONTEXT_NAME = "_NUTZ_LOADING_CONTEXT_";

	void load(GameConfig config);

	void depose(GameConfig config);

}
