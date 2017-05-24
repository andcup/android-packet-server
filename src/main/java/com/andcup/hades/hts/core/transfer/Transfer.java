package com.andcup.hades.hts.core.transfer;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.exception.ConsumeException;

/**
 * Created by Amos
 * Date : 2017/5/8 16:37.
 * Description:
 */
public abstract class Transfer {

    protected HadesRootConfigure.Server server;

    public Transfer(HadesRootConfigure.Server server){
        this.server = server;
    }

    public abstract void abort();

    public abstract void dlFromRemote(String src, String dst) throws ConsumeException;

    public abstract void upToRemote(String src, String dst) throws ConsumeException;
}
