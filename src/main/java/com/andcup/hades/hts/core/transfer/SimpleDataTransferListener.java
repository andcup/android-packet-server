package com.andcup.hades.hts.core.transfer;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Amos
 * Date : 2017/5/23 11:03.
 * Description:
 */
public class SimpleDataTransferListener implements FTPDataTransferListener {

    static  final Logger sLogger = LoggerFactory.getLogger(SimpleDataTransferListener.class.getName());

    String remote;
    String src;
    String dst;
    String tag;

    long length ;
    long transferred;
    long progress;

    public SimpleDataTransferListener(String tag, String remote, String src, String dst, long size){
        this.remote = remote;
        this.src    = src;
        this.dst    = dst;
        this.tag    = tag;
        this.length = size;
    }

    @Override
    public void started() {
        sLogger.info(tag + " from " + remote + " src = " + src + " dst = " + dst + " started!");
    }

    @Override
    public void transferred(int i) {
        transferred += i;
        int progress = (int) (transferred * 100 / length);
        if( SimpleDataTransferListener.this.progress != progress){
            SimpleDataTransferListener.this.progress = progress;
            sLogger.info(tag + " from " + remote + " src = " + src + " dst = " + dst + " progress = " + progress + "%");
        }
    }

    @Override
    public void completed() {
        sLogger.info(tag + " from " + remote + " src = " + src + " dst = " + dst + " complete!");
    }

    @Override
    public void aborted() {
        sLogger.info(tag + " from " + remote + " src = " + src + " dst = " + dst + " abort!");
    }

    @Override
    public void failed() {
        sLogger.info(tag + " from " + remote + " src = " + src + " dst = " + dst + " failed!");
    }
}
