package com.andcup.hades.hts.core.transfer;

import com.andcup.hades.hts.HadesRootConfigure;

/**
 * Created by Amos
 * Date : 2017/5/23 10:02.
 * Description:
 */
public class FtpTransferTest {

    FtpTransfer transfer;

    public FtpTransferTest(){
        HadesRootConfigure.init("Broker.json");
        transfer = new FtpTransfer(HadesRootConfigure.sInstance.remote.ftp);
    }

    @org.junit.Test
    public void abort() throws Exception {

    }

    @org.junit.Test
    public void dlFromRemote() throws Exception {
        transfer.dlFromRemote("/test/59YX/SGZT_0.apk", "sgzt.apk");
    }

    @org.junit.Test
    public void upToRemote() throws Exception {
        transfer.upToRemote("sgzt.apk", "/test/59YX/SGZT_upload.apk");
    }

    @org.junit.Test
    public void changeFileWorkDir() throws Exception {

    }

}