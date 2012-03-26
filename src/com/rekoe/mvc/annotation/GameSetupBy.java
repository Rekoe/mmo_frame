/**
 * 
 */
package com.rekoe.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rekoe.mvc.GameSetUp;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface GameSetupBy {
	Class<? extends GameSetUp> value() default GameSetUp.class;

	String[] args() default {};
}
