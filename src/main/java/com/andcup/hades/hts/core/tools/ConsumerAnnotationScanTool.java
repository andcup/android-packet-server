package com.andcup.hades.hts.core.tools;

import com.andcup.hades.hts.core.annotation.Consumer;
import com.andcup.hades.httpserver.utils.ScanForClasses;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Amos
 * Date : 2017/5/5 18:18.
 * Description:
 */
public class ConsumerAnnotationScanTool {

    public static List<Class> getClazzFromPackage( String packageName ) {
        // 是否循环搜索子包
        boolean recursive = true;
        // 包名对应的路径名称
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                List<String> classes = null;
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    classes = ScanForClasses.getClassNameByFile(url.getPath(), null, true);
                } else if ("jar".equals(protocol)) {
                    classes = ScanForClasses.getClassNameByJar(url.getPath(), true);
                }
                return loadClass(classes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Class> loadClass(List<String> classList){
        List<Class> classes = new ArrayList<>();
        for(String clazz : classList){
            try {
                Class<?> foundClazz = Thread.currentThread().getContextClassLoader().loadClass(clazz);
                if(foundClazz.isAnnotationPresent(Consumer.class)){
                    classes.add(foundClazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(new FileFilter() {

            public boolean accept(File file) {
                boolean acceptDir = recursive && file.isDirectory();   // 接受dir目录
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
                return acceptDir || acceptClass;
            }
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> foundClazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
                    if(foundClazz.isAnnotationPresent(Consumer.class)){
                        clazzs.add(foundClazz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
