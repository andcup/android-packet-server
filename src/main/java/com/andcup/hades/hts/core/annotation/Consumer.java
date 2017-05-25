package com.andcup.hades.hts.core.annotation;

import com.andcup.hades.hts.core.model.State;
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

    /**
     * 消费类型。
     * */
    Topic topic() default Topic.DOWNLOADING;
    /**
     * 绑定下一个消费类型。
     * */
    Topic bind() default Topic.DOWNLOADING;
    /**
     * 消费指定的消息。
     * */
    int   match() default Integer.MAX_VALUE;
    /**
     * 上一个消费者状态。
     * */
    State last() default State.SUCCESS;

}
