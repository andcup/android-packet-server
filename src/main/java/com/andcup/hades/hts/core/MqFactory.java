package com.andcup.hades.hts.core;

import com.andcup.hades.hts.config.HadesRootConfig;
import com.andcup.hades.hts.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.base.IMqFactory;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.tools.FileUtils;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.OKHttpClient;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
/**
 * Created by Amos
 * Date : 2017/5/5 15:46.
 * Description:
 */
public abstract class MqFactory<T> implements IMqFactory<T> {

    protected T body;

    FileInfo fileInfo;

    public MqFactory(T t){
        this.body = t;
    }

    protected abstract String getFilePath();

    public boolean checkFileIsExist() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.sourcePath = getFilePath();
        //获取文件信息.
        ResponseEntity<FileInfo> fileInfoEntity = new OKHttpClient(HadesRootConfig.sInstance.remote.file.url)
                .call(JsonConvertTool.toString(fileInfo),
                        JsonConvertTool.getCollectionType(ResponseEntity.class, FileInfo.class));
        //判断文件是否存在.
        if (fileInfoEntity.getCode() != ResponseEntity.SUCCESS) {
            return false;
        }
        this.fileInfo = fileInfoEntity.body;
        return true;
    }

    public boolean checkFileIsLatest() {
        //文件信息存储位置.
        String fileInfoPath = HadesRootConfig.sInstance.getApkTempDir() + getGroupId() + ".json";
        FileInfo localFileInfo = JsonConvertTool.toJson(new File(fileInfoPath), FileInfo.class);
        if( null != localFileInfo ){
            if(fileInfo.lastEditTime == localFileInfo.lastEditTime){
                //恢复本地文件状态.
                fileInfo.hasCompile = localFileInfo.hasCompile;
                fileInfo.downloaded = localFileInfo.downloaded;
            }else{
                return false;
            }
        }
        //保存配置文件.
        FileUtils.store(fileInfoPath, fileInfo);
        return true;
    }
}
