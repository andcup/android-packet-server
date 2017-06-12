package com.andcup.hades.hts.boot.controller;

import com.andcup.hades.hts.boot.HadesApplication;
import com.andcup.hades.hts.boot.model.FileSyncModel;
import com.andcup.hades.hts.core.MqManager;
import com.andcup.hades.hts.core.exception.ConsumeException;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import com.andcup.hades.hts.core.transfer.Transfer;
import com.andcup.hades.hts.core.transfer.ftp4j.Ftp4JTransfer;
import com.andcup.hades.httpserver.HadesHttpResponse;
import com.andcup.hades.httpserver.RequestController;
import com.andcup.hades.httpserver.bind.Body;
import com.andcup.hades.httpserver.bind.Controller;
import com.andcup.hades.httpserver.bind.Request;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.IOException;

/**
 * Created by Amos
 * Date : 2017/6/1 14:10.
 * Description:
 */

@Controller("/api/file")
public class FileSyncController extends RequestController {

    MqManager<FileSyncModel> mqManager = new MqManager<>();

    UploadFileThread uploadThread = new UploadFileThread();

    @Request(value = "/sync", method = Request.Method.POST)
    public HadesHttpResponse syncFile(@Body(FileSyncModel.class) FileSyncModel model){
        if( null == model.localFilePath || model.localFilePath.length() <= 0){
            return new HadesHttpResponse(0, "文件不存在.");
        }
        if(model.localFilePath.startsWith("/")){
            model.localFilePath = "." + model.localFilePath;
        }
        if(!new File(model.localFilePath).exists()){
            return new HadesHttpResponse(-1, model.localFilePath + " 文件不存在.");
        }
        mqManager.push(model);
        startIfNeed();
        return new HadesHttpResponse(0, "任务提交成功");
    }

    private void startIfNeed(){
        if(!uploadThread.isAlive()){
            uploadThread.start();
        }
    }

    public class UploadFileThread extends Thread{
        @Override
        public void run() {
            while (true){
                FileSyncModel model = mqManager.pop();
                if( null != model ){
                    Response response = new Response();
                    response.attachData = model.attachData;
                    try{
                        Transfer transfer = new Ftp4JTransfer(HadesApplication.sInstance.f().to);
                        transfer.upToRemote(model.localFilePath, model.remoteFilePath);
                        response.code = 0;
                        response.msg  = "同步成功.";
                    } catch (ConsumeException e){
                        response.code = 1;
                        response.msg  = "同步失败 : " + e.getMessage();
                    }
                    try {
                        new OKHttpClient(model.feedbackApiAddress).call(JsonConvertTool.toString(response));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Response{
        @JsonProperty("attachData")
        String attachData;
        @JsonProperty("error")
        int code;
        @JsonProperty("errorMessage")
        String msg;
    }
}
