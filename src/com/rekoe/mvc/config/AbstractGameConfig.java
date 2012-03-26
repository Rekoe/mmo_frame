package com.rekoe.mvc.config;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfigException;
import org.nutz.mvc.config.AtMap;
import org.nutz.mvc.impl.NutLoading;

import com.rekoe.mvc.Loading;
import com.rekoe.mvc.annotation.GameLoadingBy;


public abstract class AbstractGameConfig implements GameConfig {


	private static final Log log = Logs.get();
	
	public AbstractGameConfig() {
		//Scans.me().init(context);
	}

	public Loading createLoading() {
		/*
		 * 确保用户声明了 MainModule
		 */
		Class<?> mainModule = getMainModule();
		
		/*
		 * 获取 Loading
		 */
		GameLoadingBy by = mainModule.getAnnotation(GameLoadingBy.class);
		if (null == by) {
			if (log.isDebugEnabled()){
				log.debug("Loading by " + NutLoading.class);
			}
			return new GameLoading();
		}
		try {
			if (log.isDebugEnabled()){
				log.debug("Loading by born " + by.value());
			}
			return Mirror.me(by.value()).born();
		}
		catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}
	public Ioc getIoc() {
		return GameMvcs.getIoc();
	}

	public Class<?> getMainModule() {
		String name = Strings.trim("com.rekoe.server.MainModule");
		try {
			Class<?> mainModule = null;
			if (!Strings.isBlank(name)){
				mainModule = Lang.loadClass(name);
			}
			if (null == mainModule) {
				throw new NutConfigException("You need declare modules parameter in your context configuration file!");
			} else if (log.isDebugEnabled())
				log.debugf("MainModule: <%s>", mainModule.getName());
			return mainModule;
		}
		catch (Exception e) {
			throw new NutConfigException(e);
		}
	}

	public AtMap getAtMap() {
		return GameMvcs.getAtMap();
	}

	protected List<String> enum2list(Enumeration<?> enums) {
		LinkedList<String> re = new LinkedList<String>();
		while (enums.hasMoreElements())
			re.add(enums.nextElement().toString());
		return re;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAttributeAs(Class<T> type, String name) {
		Object obj = getAttribute(name);
		if (null == obj)
			return null;
		if (type.isInstance(obj))
			return (T) obj;
		return Castors.me().castTo(obj, type);
	}
}
