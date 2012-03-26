package com.rekoe.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rekoe.mvc.GameIocProvider;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface GameIocBy {
	/**
	 * 通过 IocProvider，来决定采用何种方式的 Ioc 容器
	 */
	Class<? extends GameIocProvider> type();

	/**
	 * 这个参数将传递给 IocProvider 的 create 方法，作为构造 Ioc 容器必要的参数
	 * <p>
	 * 不同的 IocProvider 对参数数组的具体要求是不一样的，具体请参看各个 IocProvider 的说明
	 */
	String[] args();
}
