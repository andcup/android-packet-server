package com.andcup.hades.hts.core.annotation;

import com.andcup.hades.hts.core.model.Topic;
import com.sun.deploy.security.ValidationState;

import java.lang.annotation.*;

/**
 * Created by Amos
 * Date : 2017/5/5 17:28.
 * Description:
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Consumer {

    Topic topic() default Topic.DOWNLOADING;

    Topic bind() default Topic.DOWNLOADING;

    Level level() default Level.LEVEL_ALL;

    enum Level{
        LEVEL_ALL,
        LEVEL_0,
        LEVEL_1,
        LEVEL_2,
    }
}
