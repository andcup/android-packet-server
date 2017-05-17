package com.andcup.hades.hts.boot.core.bind;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Controller {

	String name() default "";
}
