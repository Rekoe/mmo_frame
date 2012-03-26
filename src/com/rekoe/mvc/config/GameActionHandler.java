package com.rekoe.mvc.config;


public class GameActionHandler {


	private com.rekoe.mvc.Loading loading;

	private GameConfig config;

	public GameActionHandler(GameConfig config) {
		this.config = config;
		this.loading = config.createLoading();
		loading.load(config);
	}

	public boolean handle() {
		return true;
	}

	public void depose() {
		loading.depose(config);
	}


}
