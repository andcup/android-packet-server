package com.andcup.hades.hts.core.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipBreakException;
import org.zeroturnaround.zip.ZipInfoCallback;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/8/4.
 */
public class MetaInfMatcher {

    Logger logger = LoggerFactory.getLogger(MetaInfMatcher.class.getName());

     static String SF  = "SF";
    static String RSA = "RSA";
    static boolean found = false;
    public static   boolean match(String zip){
        found = false;
        ZipUtil.iterate(new File(zip),  new ZipInfoCallback(){
            @Override
            public void process(ZipEntry zipEntry) throws IOException {
                ZipEntry temp = zipEntry;
                if(temp.getName().contains(SF) || temp.getName().contains(RSA)){
                    found = true;
                    throw new ZipBreakException(" found SF OR RSA file");
                }
            }
        });
        return found;
    }
}
