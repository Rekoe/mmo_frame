package com.rekoe.server;

import org.nutz.mvc.annotation.Modules;

import com.rekoe.ioc.GameComboIocProvider;
import com.rekoe.mvc.annotation.GameIocBy;
import com.rekoe.mvc.annotation.GameSetupBy;

@Modules(scanPackage=true)
@GameSetupBy(MMOSetUp.class)
@GameIocBy(type=GameComboIocProvider.class,
		args={"*org.nutz.ioc.loader.json.JsonLoader","/ioc",
			  "*org.nutz.ioc.loader.annotation.AnnotationIocLoader","com.rekoe.handler"})
public class MainModule {

}
