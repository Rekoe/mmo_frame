package com.rekoe.mvc.config;

import org.nutz.ioc.Ioc;
import org.nutz.lang.util.Context;

public interface GameConfig {

	/**
	 * @return 当前应用的 IOC 容器实例
	 */
	Ioc getIoc();

	/**
	 * @return 当前应用的根路径
	 */
	String getAppRoot();

	/**
	 * @return 当前应用的名称
	 */
	String getAppName();

	/**
	 * 获取配置的主模块，一般的说是存放在 initParameter 集合下的 "modules" 属性 值为一个 class 的全名
	 * @return 配置的主模块，null - 如果没有定义这个参数
	 */
	Class<?> getMainModule();

	/**
	 * 根据 MainModule 中的 '@LoadingBy' 得到一个加载逻辑的实现类
	 * @return 加载逻辑
	 */
	com.rekoe.mvc.Loading createLoading();

	/**
	 * 加载时上下文包括环境变量，以及 "app.root" 等信息
	 */
	Context getLoadingContext();
	<T> T getAttributeAs(Class<T> type, String name);

	/**
	 * 在上下文环境中设置属性对象
	 * 
	 * @param name
	 *            属性名
	 * @param obj
	 *            属性值
	 */
	void setAttribute(String name, Object obj);
	
	/**
	 * 获取上下文环境中的属性对象
	 * 
	 * @param name
	 *            - 属性名
	 * 
	 * @return 值
	 */
	Object getAttribute(String name);
}
