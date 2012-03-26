package com.rekoe.mvc;

import com.rekoe.mvc.config.GameConfig;

public interface GameSetUp {
	/**
	 * 启动时，额外逻辑
	 * 
	 * @param config
	 */
	void init(GameConfig config);

	/**
	 * 关闭时，额外逻辑
	 * 
	 * @param config
	 */
	void destroy(GameConfig config);
}
