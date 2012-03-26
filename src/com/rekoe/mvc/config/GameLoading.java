package com.rekoe.mvc.config;

import java.io.File;
import java.util.Map.Entry;

import org.nutz.Nutz;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.Ioc2;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Encoding;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.Segments;
import org.nutz.lang.util.Context;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Loading;
import org.nutz.mvc.LoadingException;

import com.rekoe.mvc.GameSetUp;
import com.rekoe.mvc.annotation.GameIocBy;
import com.rekoe.mvc.annotation.GameSetupBy;

public class GameLoading implements com.rekoe.mvc.Loading{

	private static final Log log = Logs.get();

	public void load(GameConfig config) {
		if (log.isInfoEnabled()) {
			log.infof("Nutz Version : %s ", Nutz.version());
			log.infof("Nutz.Mvc[%s] is initializing ...", config.getAppName());
		}
		if (log.isDebugEnabled()) {
			log.debug("Web Container Information:");
			log.debugf(" - Default Charset : %s", Encoding.defaultEncoding());
			log.debugf(" - Current . path  : %s", new File(".").getAbsolutePath());
			log.debugf(" - Java Version    : %s", System.getProperties().get("java.version"));
			log.debugf(" - File separator  : %s", System.getProperties().get("file.separator"));
			log.debugf(" - Timezone        : %s", System.getProperties().get("user.timezone"));
		}
		/*
		 * 准备计时
		 */
		Stopwatch sw = Stopwatch.begin();

		try {

			/*
			 * 检查主模块，调用本函数前，已经确保过有声明 MainModule 了
			 */
			Class<?> mainModule = config.getMainModule();

			/*
			 * 创建上下文
			 */
			createContext(config);

			/*
			 * 检查 Ioc 容器并创建和保存它
			 */
			createIoc(config, mainModule);


			/*
			 * 执行用户自定义 Setup
			 */
			evalSetup(config, mainModule);
		}
		catch (Exception e) {
			if (log.isErrorEnabled())
				log.error("Error happend during start serivce!", e);
			throw Lang.wrapThrow(e, LoadingException.class);
		}

		// ~ Done ^_^
		sw.stop();
		if (log.isInfoEnabled())
			log.infof("Nutz.Mvc[%s] is up in %sms", config.getAppName(), sw.getDuration());
	}
	


	private static void createContext(GameConfig config) {
		// 构建一个上下文对象，方便子类获取更多的环境信息
		// 同时，所有 Filter 和 Adaptor 都可以用 ${app.root} 来填充自己
		Context context = Lang.context();
		context.set("app.root", config.getAppRoot());

		if (log.isDebugEnabled()) {
			log.debugf(">> app.root = %s", config.getAppRoot());
		}

		// 载入环境变量
		for (Entry<String, String> entry : System.getenv().entrySet())
			context.set("env." + entry.getKey(), entry.getValue());
		// 载入系统变量
		for (Entry<Object, Object> entry : System.getProperties().entrySet())
			context.set("sys." + entry.getKey(), entry.getValue());

		if (log.isTraceEnabled()) {
			log.tracef(">>\nCONTEXT %s", Json.toJson(context, JsonFormat.nice()));
		}
		GameMvcs.setContext(Loading.CONTEXT_NAME, context);
	}

	private void evalSetup(GameConfig config, Class<?> mainModule) throws Exception {
		GameSetupBy sb = mainModule.getAnnotation(GameSetupBy.class);
		if (null != sb) {
			if (log.isInfoEnabled())
				log.info("Setup application...");
			GameSetUp setup = evalObj(config, sb.value(), sb.args());
			setup.init(config);
		}
	}
	public static <T> T evalObj(GameConfig config, Class<T> type, String[] args) {
		// 用上下文替换参数
		Context context = config.getLoadingContext();
		for (int i = 0; i < args.length; i++) {
			args[i] = Segments.replace(args[i], context);
		}
		// 判断是否是 Ioc 注入

		if (args.length == 1 && args[0].startsWith("ioc:")) {
			String name = Strings.trim(args[0].substring(4));
			return config.getIoc().get(type, name);
		}
		return Mirror.me(type).born((Object[]) args);
	}

	private void createIoc(GameConfig config, Class<?> mainModule) throws Exception {
		GameIocBy ib = mainModule.getAnnotation(GameIocBy.class);
		if (null != ib) {
			if (log.isDebugEnabled())
				log.debugf("@GameIocBy(%s)", ib.type().getName());

			Ioc ioc = Mirror.me(ib.type()).born().create(config, ib.args());
			// 如果是 Ioc2 的实现，增加新的 ValueMaker
			if (ioc instanceof Ioc2) {
				//((Ioc2) ioc).addValueProxyMaker(new ServletValueProxyMaker(config.getServletContext()));
			}
			// 保存 Ioc 对象
			GameMvcs.setIoc(ioc);

		} else if (log.isInfoEnabled())
			log.info("!!!Your application without @GameIocBy supporting");
	}


	public void depose(GameConfig config) {
		if (log.isInfoEnabled())
			log.infof("Nutz.Mvc[%s] is deposing ...", config.getAppName());
		Stopwatch sw = Stopwatch.begin();

		// Firstly, upload the user customized desctroy
		try {
			GameSetUp setup = config.getAttributeAs(GameSetUp.class, GameSetUp.class.getName());
			if (null != setup)
				setup.destroy(config);
		}
		catch (Exception e) {
			throw new LoadingException(e);
		}
		finally {
			// If the application has Ioc, depose it
			Ioc ioc = config.getIoc();
			if (null != ioc)
				ioc.depose();
		}

		// Done, print info
		sw.stop();
		if (log.isInfoEnabled())
			log.infof("Nutz.Mvc[%s] is down in %sms", config.getAppName(), sw.getDuration());
	}
}
