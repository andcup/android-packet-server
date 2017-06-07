package com.andcup.hades.httpserver.bind;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Controller {

	String value() default "";
}
