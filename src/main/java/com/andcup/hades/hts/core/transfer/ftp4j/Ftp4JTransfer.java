package com.andcup.hades.hts.core.transfer.ftp4j;

import com.andcup.hades.hts.HadesRootConfigure;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.transfer.Transfer;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Amos
 * Date : 2017/5/8 16:42.
 * Description:
 */
public class Ftp4JTransfer extends Transfer {

    FTPClient client;

    public Ftp4JTransfer(HadesRootConfigure.Server server) {
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
            throw new ConsumeException(getServer() +  src + " IOException=> " + e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + src + " FTPAbortedException=> " + e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + src + " FTPException=> " + e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + src + " FTPDataTransferException=> " + e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + src + " FTPIllegalReplyException=> " + e.getMessage());
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
            throw new ConsumeException(getServer() + dst + " IOException=>" + e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + dst +  " FTPIllegalReplyException=>" + e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  dst + " FTPException=>" +e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() +  dst + " FTPDataTransferException=>" + e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + dst +  " FTPAbortedException=>" + e.getMessage());
        } finally {
            logout();
        }
    }

    private String getServer(){
        return server.url + ":" + server.port;
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
            client.setPassive(true);
            client.setType(FTPClient.TYPE_BINARY);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConsumeException(getServer() + " login failed : " +e.getMessage());
        }
    }

    private void logout(){
        if( null == client){
            return;
        }
        try {
            if(client.isConnected()){
                client.disconnect(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }finally {
            client = null;
        }
    }
}