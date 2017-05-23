package com.andcup.hades.hts.core.tools;

import java.io.File;

/**
 * Created by Amos
 * Date : 2017/5/23 14:30.
 * Description:
 */
public class MakeDirTool {

    public static void mkdir(String dir){
        String[] dirs = dir.split("/");
        String prefix = "";
        for(String newDir : dirs){
            if(!newDir.equals("")){
                prefix += newDir;
                new File(prefix).mkdir();
                prefix += "/";
            }
        }
    }

    public static void mkdirByPath(String path){
        int index = path.lastIndexOf('/');
        if(index != -1){
            mkdir(path.substring(0, index));
        }
    }
}
