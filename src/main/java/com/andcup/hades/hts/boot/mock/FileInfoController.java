package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.web.controller.cps.model.ResponseEntity;
import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/5 20:36.
 * Description:
 */


@RequestMapping(name = "/api/fileInfo")
public class FileInfoController extends Controller<FileInfo> {

    private void check(FileInfo entity) throws RuntimeException{
        if(entity.sourcePath.length() <= 0){
            throw new RuntimeException("sourcePath is null.");
        }

    }

    protected ResponseEntity onHandle(FileInfo body) {
        try{
            logger.info(JsonConvertTool.toString(body));
            /**检查参数.*/
            check(body);

            FileInfo fileInfo = new FileInfo();
            File file = new File(body.sourcePath);
            if(file.exists()){
                fileInfo.sourcePath = body.sourcePath;
                fileInfo.fileSize = file.length();
                fileInfo.lastEditTime = file.lastModified();
                ResponseEntity<FileInfo> fileResponse = new ResponseEntity<FileInfo>(ResponseEntity.SUCCESS, "get file success.");
                fileResponse.body = fileInfo;
                logger.info(JsonConvertTool.toString(fileResponse));
                return fileResponse;
            }
        }catch (Exception e){
        }
        ResponseEntity responseEntity = new ResponseEntity(ResponseEntity.ERR_PARAM, " file is not exist.");
        logger.info(JsonConvertTool.toString(responseEntity));
        return responseEntity;
    }

    protected Class<FileInfo> getModel() {
        return FileInfo.class;
    }
}
