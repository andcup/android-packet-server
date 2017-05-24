package com.andcup.hades.hts.core.transfer;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.exception.ConsumeException;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Amos
 * Date : 2017/5/8 16:42.
 * Description:
 */
public class FtpTransfer extends Transfer {

    FTPClient client;

    public FtpTransfer(HadesRootConfigure.Server server) {
        super(server);
        client = new FTPClient();
    }

    @Override
    public void abort() {
        logout();
    }

    public void dlFromRemote(String src, String dst) throws ConsumeException{
        if( null == client || !client.isAuthenticated()){
            login();
        }
        try {
            long size = client.fileSize(src);
            client.download(src, new File(dst), new SimpleDataTransferListener("download ", server.url, src, dst, size));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " IOException=> " + e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " FTPAbortedException=> " + e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " FTPException=> " + e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " FTPDataTransferException=> " + e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " FTPIllegalReplyException=> " + e.getMessage());
        } finally {
            logout();
        }
    }

    public void upToRemote(String src, String dst) throws ConsumeException{
        if( null == client || !client.isAuthenticated()){
            login();
        }

        try {
            File file   = new File(src);
            mkdir(dst);
            client.upload(file, new SimpleDataTransferListener("upload ", server.url, src, dst, file.length()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  " IOException=>" + e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  " FTPIllegalReplyException=>" + e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  " FTPException=>" +e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  " FTPDataTransferException=>" + e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  " FTPAbortedException=>" + e.getMessage());
        } finally {
            logout();
        }
    }

    private String getServer(){
        return server.url;
    }

    private void mkdir(String dir){
        int index = dir.lastIndexOf('/');
        if(index == -1){
            /**
             * 传输到根目录.
             * */
            return;
        }
        String workDir = dir.substring(0, index + 1);
        /**
         * 获取需要创建的路径.
         * */
        String[] dirs = workDir.split("/");
        for(int i = 0; i< dirs.length; i++){
            if(dirs[i].equals("")){
                continue;
            }
            String dirTemp = dirs[i];

            makeDir(client, dirTemp);
            changeDir(client, dirTemp);
        }
    }

    private void makeDir(FTPClient client, String dir){
        try {
            /**返回到跟目录.*/
            client.createDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
    }

    private void changeDir(FTPClient client, String dir){
        try {
            /**返回到跟目录.*/
            client.changeDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
    }

    protected String changeFileWorkDir(String path){
        try {
            int index = path.lastIndexOf('/');
            if(index == -1){
                return path;
            }
            String workDir = path.substring(0, index + 1);
            client.changeDirectory(workDir);
            return path.substring(index + 1, path.length());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
        return path;
    }

    private boolean login()  {
        if (!server.url.startsWith("http://")) {
            server.url = "http://" + server.url;
        }
        try {
            URL url = new URL(server.url);
            client.connect(url.getHost(), server.port);
            client.login(server.username, server.password);
            client.setAutoNoopTimeout(60* 60 * 60);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " login failed : " +e.getMessage());
        }
    }

    private void logout(){
        try {
            client.disconnect(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }finally {
            try {
                client.abortCurrentDataTransfer(true);
                client.abortCurrentConnectionAttempt();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FTPIllegalReplyException e) {
                e.printStackTrace();
            }
            client = null;
        }
    }
}
