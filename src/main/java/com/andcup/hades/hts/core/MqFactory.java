package com.andcup.hades.hts.core;

import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.model.FileInfo;

/**
 * Created by Amos
 * Date : 2017/5/5 15:46.
 * Description:
 */
public abstract class MqFactory<T> implements IMqFactory<T> {

    protected T body;

    public MqFactory(T t){
        this.body = t;
    }
}
