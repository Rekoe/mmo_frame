package com.rekoe.handler;

import org.nutz.ioc.loader.annotation.IocBean;

@IocBean(create="init")
public class LoginHandler {

	public void init()
	{
		System.out.println("加载测试。。。");
	}
}
