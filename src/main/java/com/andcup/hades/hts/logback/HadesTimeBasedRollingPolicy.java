package com.andcup.hades.hts.logback;

import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.andcup.hades.hts.Hades;

/**
 * Created by Amos
 * Date : 2017/6/1 11:08.
 * Description:
 */
public class HadesTimeBasedRollingPolicy<E> extends TimeBasedRollingPolicy<E> {

    @Override
    public String getActiveFileName() {
        String name = super.getActiveFileName();
        return name.replace(".log", "-" + Hades.sInstance.port + ".log");
    }
}
