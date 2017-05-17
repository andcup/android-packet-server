package com.andcup.hades.hts.boot.core.bind;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Body {
    Class<?> value() default Object.class;
}
