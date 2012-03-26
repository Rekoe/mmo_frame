package com.rekoe.mvc.config;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.IocContext;
import org.nutz.lang.util.Context;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.config.AtMap;

public class GameMvcs {


	public static final String DEFAULT_MSGS = "$default";
	public static final String MSG = "msg";
	public static final String LOCALE_NAME = "nutz_mvc_locale";
	public static Map<String,AtMap> atMap = new HashMap<String,AtMap>();
	public static Map<String,Ioc> iocMap = new HashMap<String,Ioc>();
	public static Map<String,NutConfig> nutConfigMap = new HashMap<String,NutConfig>();
	public static Map<String,Context> contextMap = new HashMap<String,Context>();
	public static Map<String,Object> sysMap = new HashMap<String,Object>();

	// 新的,基于ThreadLoacl改造过的Mvc辅助方法
	// ====================================================================

	public static Ioc getIoc() {
		return iocMap.get(getName() + "_ioc");
	}

	public static void setAttribute(String name, Object obj)
	{
		sysMap.put(name, obj);
	}
	public static Object getAttribute(String name)
	{
		return sysMap.get(name);
	}
	
	public static void setIoc(Ioc ioc) {
		iocMap.put(getName() + "_ioc", ioc);
	}

	//contextMap
	public static void setContext(String key, Context context) {
		contextMap.put(key, context);
	}
	public static AtMap getAtMap() {
		return atMap.get(getName() + "_atmap");
	}

	public static void setAtMap(AtMap atmap) {
		atMap.put(getName() + "_atmap", atmap);
	}

	public static void setNutConfig(NutConfig config) {
		nutConfigMap.put(getName() + "_mvc_config", config);
	}

	public static NutConfig getNutConfig() {
		return nutConfigMap.get(getName() + "_mvc_config");
	}

	// 将当前请求的主要变量保存到ThreadLocal, by wendal
	// ==================================================================
	private static final ThreadLocal<String> NAME = new ThreadLocal<String>();
	private static final ThreadLocal<ActionContext> ACTION_CONTEXT = new ThreadLocal<ActionContext>();
	private static final ThreadLocal<IocContext> IOC_CONTEXT = new ThreadLocal<IocContext>();

	public static final String getName() {
		return NAME.get();
	}

	public static final ActionContext getActionContext() {
		return ACTION_CONTEXT.get();
	}


	public static void setActionContext(ActionContext actionContext) {
		ACTION_CONTEXT.set(actionContext);
	}

	public static void setIocContext(IocContext iocContext) {
		IOC_CONTEXT.set(iocContext);
	}

	public static IocContext getIocContext() {
		return IOC_CONTEXT.get();
	}

	// ==================================================================

	// 重置当前线程所持有的对象
	public static void resetALL() {
		ACTION_CONTEXT.set(null);
		NAME.set(null);
		IOC_CONTEXT.set(null);
	}

}
