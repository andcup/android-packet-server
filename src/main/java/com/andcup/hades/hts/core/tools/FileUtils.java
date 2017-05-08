package com.andcup.hades.hts.core.tools;


import java.io.*;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/7/18.
 */
public class FileUtils {

    public static <T> T load(String filepath, Class<T> clazz){
        InputStream in = null;
        try {
            File file = new File(filepath);
            byte[] tempbytes = new byte[(int) file.length()];
            in = new FileInputStream(filepath);
            in.read(tempbytes);
            return JsonConvertTool.toJson(new String(tempbytes), clazz);
        } catch (Exception e1) {
            //e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static void store(String filepath, Object store){
        FileOutputStream fop = null;
        File file;
        try {
            file = new File(filepath);
            file.delete();
            file.createNewFile();
            fop = new FileOutputStream(file);
            byte[] contentInBytes = store.toString().getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void log(String filepath, Object store){
        FileOutputStream fop = null;
        File file;
        try {
            file = new File(filepath);
            fop  = new FileOutputStream(file, true);
            byte[] contentInBytes = store.toString().getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
