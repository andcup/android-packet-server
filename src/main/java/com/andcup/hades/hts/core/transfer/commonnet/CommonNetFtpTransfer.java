package com.andcup.hades.hts.core.transfer.commonnet;

import com.andcup.hades.hts.config.F;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.transfer.Transfer;
import com.andcup.hades.httpserver.utils.LogUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/5/27 13:53.
 * Description:
 */
public class CommonNetFtpTransfer extends Transfer {

    FTPClient ftpClient;

    public CommonNetFtpTransfer(F.Server server) {
        super(server);
    }

    @Override
    public void abort() {

    }

    @Override
    public void dlFromRemote(String src, String dst) throws ConsumeException {
        if(!login()){
            throw  new ConsumeException(" login " + server.url + " failed");
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(dst);
            LogUtils.info(CommonNetFtpTransfer.class, " download : " + src + " to : " + dst);
            ftpClient.retrieveFile(src, outputStream);
            LogUtils.info(CommonNetFtpTransfer.class, " download : " + src + " to : " + dst + " success. ");
        } catch (IOException e) {
            e.printStackTrace();
            String error = " download : " + src + " to : " + dst + " error. " + e.getMessage();
            LogUtils.info(CommonNetFtpTransfer.class, error);
            throw new ConsumeException(error);
        } finally {
            try{
                outputStream.close();
            }catch (IOException e){

            }
            close();
        }
    }

    @Override
    public void upToRemote(String src, String dst) throws ConsumeException {

    }

    private boolean login(){
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server.url, server.port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(server.username, server.password);//登录
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                LogUtils.info(CommonNetFtpTransfer.class, "login ftp " + server.url +  "failed. replyCode:" + reply);
                close();
                return false;
            }
            LogUtils.info(CommonNetFtpTransfer.class, "login ftp " + server.url +  " success");
            /**
             * 设置二进制上传
             * */
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            int defaultTimeoutSecond = 5;
            ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
            ftpClient.setConnectTimeout(defaultTimeoutSecond * 1000);
            ftpClient.setDataTimeout(defaultTimeoutSecond * 1000);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.info(CommonNetFtpTransfer.class, e.getMessage());
        } finally {
        }
        return false;
    }

    public void close(){
        if(null != ftpClient && ftpClient.isConnected()) {
            try {
                boolean logout = ftpClient.logout();
                LogUtils.info(CommonNetFtpTransfer.class, "logout = " + logout);
            } catch (IOException e) {
                e.printStackTrace();
                LogUtils.info(CommonNetFtpTransfer.class, e.getMessage());
            }finally {
                try {
                    ftpClient.disconnect();
                    LogUtils.info(CommonNetFtpTransfer.class, "disconnect ftp..");
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.info(CommonNetFtpTransfer.class, e.getMessage());
                }
                ftpClient = null;
            }
        }
    }
}
