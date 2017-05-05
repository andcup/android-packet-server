package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqFactory;

/**
 * Created by Amos
 * Date : 2017/5/5 15:46.
 * Description:
 */
public abstract class MqFactory<T> implements IMqFactory<T> {

    T t;

    public MqFactory(T t){
        this.t = t;
    }
}
