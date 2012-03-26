package com.rekoe.server;

import org.nutz.ioc.Ioc;

import com.rekoe.handler.LoginHandler;
import com.rekoe.mvc.GameSetUp;
import com.rekoe.mvc.config.GameConfig;

public class MMOSetUp implements GameSetUp{

	@Override
	public void init(GameConfig config) {
		Ioc ioc = config.getIoc();
		LoginHandler login = ioc.get(LoginHandler.class);
		System.out.println("ioc ...."+login);
	}

	@Override
	public void destroy(GameConfig config) {
		
	}

}
