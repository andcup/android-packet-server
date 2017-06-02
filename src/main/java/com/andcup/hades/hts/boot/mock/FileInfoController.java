package com.andcup.hades.hts.boot.mock;

import com.andcup.hades.hts.core.model.FileInfo;
import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.core.tools.MD5;
import com.andcup.hades.hts.server.HadesHttpResponse;
import com.andcup.hades.hts.server.RequestController;
import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.bind.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/25 11:03.
 * Description:
 */
@Controller("/api/file")
public class FileInfoController extends RequestController {

    Logger logger = LoggerFactory.getLogger(FileInfoController.class.getName());

    @Request(value = "/getFileInfo", method = Request.Method.GET)
    public HadesHttpResponse<FileInfo> getFileInfo(@Var("sourcePath") String sourcePath){
        FileInfo fileInfo = new FileInfo();
        if(sourcePath.startsWith("/")){
            sourcePath = sourcePath.substring(1, sourcePath.length());
        }
        File file = new File(sourcePath);
        if(file.exists()){
            fileInfo.fileSize = file.length();
            fileInfo.lastEditTime = file.lastModified();
            fileInfo.md5 = MD5.toMd5(sourcePath + fileInfo.fileSize + fileInfo.lastEditTime);
            logger.info(JsonConvertTool.toString(fileInfo));
            return new HadesHttpResponse<FileInfo>(0,  fileInfo);
        }else{
            return new HadesHttpResponse(-1,  sourcePath + " 文件不存在.");
        }
    }
}
