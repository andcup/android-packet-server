package com.andcup.hades.hts.core.tools;

import java.util.Iterator;

/**
 * Created by Amos
 * Date : 2017/5/27 16:59.
 * Description:
 */
public class MessageTools {

    public static <T> void merge(Iterator<T> t1, Iterator<T> t2){
        while (t1.hasNext()) {
            T value = t1.next();
            while (t2.hasNext()) {
                T queueValue = t2.next();
                if(queueValue.equals(value)){
                    t1.remove();
                }
            }
        }
    }
}
