package com.andcup.hades.hts.core.transfer;

import com.andcup.hades.hts.Hades;
import com.andcup.hades.hts.core.transfer.ftp4j.Ftp4JTransfer;

/**
 * Created by Amos
 * Date : 2017/5/23 10:02.
 * Description:
 */
public class Ftp4JTransferTest {

    Ftp4JTransfer transfer;

    public Ftp4JTransferTest(){
        Hades.init("config.json", null);
        transfer = new Ftp4JTransfer(Hades.sInstance.f.from);
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