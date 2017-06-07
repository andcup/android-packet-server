package com.andcup.hades.httpserver.utils;

import java.io.File;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/9/6.
 */
public class CacheClear {


    public static void delete(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1 : files){
                delete(file1);
            }
        }
        file.delete();
    }
}
