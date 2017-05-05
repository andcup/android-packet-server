package com.andcup.hades.hts.core.annotation;

import com.andcup.hades.hts.core.model.Topic;

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

    Topic topic() default Topic.CHECK_FILE_EXIST;

    Topic bind() default Topic.CHECK_FILE_EXIST;
}
