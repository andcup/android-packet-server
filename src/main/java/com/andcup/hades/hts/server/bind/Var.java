package com.andcup.hades.hts.server.bind;

import java.lang.annotation.*;

/**
 * Created by Amos
 * Date : 2017/5/17 17:19.
 * Description:
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Var {
    String value() default "";
}
