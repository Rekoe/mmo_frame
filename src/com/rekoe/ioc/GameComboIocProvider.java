package com.rekoe.ioc;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.ScopeContext;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.lang.Lang;

import com.rekoe.mvc.GameIocProvider;
import com.rekoe.mvc.config.GameConfig;

public class GameComboIocProvider implements GameIocProvider{

	@Override
	public Ioc create(GameConfig config, String[] args) {
		try {
			return new NutIoc(new ComboIocLoader(args), new ScopeContext("mmo"), "mmo");
		}
		catch (ClassNotFoundException e) {
			throw Lang.wrapThrow(e);
		}
	}

}
