package com.rekoe.server;

import com.rekoe.mvc.config.FilterGameConfig;
import com.rekoe.mvc.config.GameActionHandler;

public class Server {
	private GameActionHandler handler;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		FilterGameConfig config = new FilterGameConfig();
		server.handler = new GameActionHandler(config);
		server.handler.handle();
	}
}
