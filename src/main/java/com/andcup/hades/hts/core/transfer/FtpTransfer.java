package com.andcup.hades.hts.core.transfer;

import com.andcup.hades.hts.config.HadesRootConfig;
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

    long length ;
    long tran;
    long progress;


    public FtpTransfer(HadesRootConfig.Server server) {
        super(server);
        client = new FTPClient();
    }

    @Override
    public void abort() {
        logout();
    }

    public void dlFromRemote(String src, String dst) throws ConsumeException{
        try {
            String fileName = changeFileWorkDir(src);
            FTPFile[] fs = client.list();
            for(FTPFile ff:fs){
                if(ff.getName().equals(fileName)){
                    File remoteFile = new File(dst);
                    client.download(ff.getName(), remoteFile);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } catch (FTPListParseException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException(e.getMessage());
        } finally {
            logout();
        }
    }

    public void upToRemote(String src, String dst) throws ConsumeException{
        if( null == client){
            login();
        }

        try {
            File file   = new File(src);
            length = file.length();
            client.upload(file, new FTPDataTransferListener() {
                public void started() {
                }

                public void transferred(int i) {
                    tran += i;
                    int progress = (int) (tran * 100 / length);
                    if( FtpTransfer.this.progress != progress){
                        FtpTransfer.this.progress = progress;
                    }
                }

                public void completed() {
                }

                public void aborted() {
                }

                public void failed() {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConsumeException("IOException=>" + e.getMessage());
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            throw new ConsumeException("FTPIllegalReplyException=>" + e.getMessage());
        } catch (FTPException e) {
            e.printStackTrace();
            throw new ConsumeException("FTPException=>" +e.getMessage());
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            throw new ConsumeException("FTPDataTransferException=>" + e.getMessage());
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            throw new ConsumeException("FTPAbortedException=>" + e.getMessage());
        }
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
            throw new ConsumeException(e.getMessage());
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
