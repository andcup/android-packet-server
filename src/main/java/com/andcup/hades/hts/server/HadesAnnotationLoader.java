package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/15 14:42.
 * Description:
 */
class HadesAnnotationLoader {

    final static Logger sLogger = LoggerFactory.getLogger(HadesAnnotationLoader.class);

    public Map<String, RequestInvoker> loadMethod(String packageName)  {

        Map<String, RequestInvoker> methodMap = new HashMap<>();

        List<String> classList = getClassByPackageName(packageName);
        for (String str : classList) {
            try {
                Class  c = Class.forName(str);
                if (c.isAnnotationPresent(Controller.class)) {
                    Method[] methods = c.getMethods();
                    for(Method method : methods){
                        if(method.isAnnotationPresent(Request.class)){
                            RequestInvoker invoker = new RequestInvoker();
                            invoker.controller = (Controller) c.getAnnotation(Controller.class);
                            invoker.clazz = c;
                            invoker.request = method.getAnnotation(Request.class);
                            invoker.path = invoker.controller.value() + invoker.request.value();
                            invoker.method = method;
                            if(!invoker.method.getReturnType().isAssignableFrom(HadesHttpResponse.class)){
                                sLogger.error(" Request method : " + invoker.path + " return type must be HadesInvokeResponse");
                                continue;
                            }
                            /**
                             * 添加Controller到列表中.
                             * */
                            methodMap.put(invoker.path, invoker);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return methodMap;
    }

    public List<String> getClassByPackageName(String packageName) {
        String path = packageName.replace(".", "/") + "/";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(path);

        if( null != url ){
            String type = url.getProtocol();
            if (type.equals("file")) {
                return getClassNameByFile(url.getPath(), null, true);
            }
        }
        return null;
    }

    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    if(childFilePath.startsWith("main.")){
                        childFilePath = childFilePath.substring("main.".length());
                    }
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }
}
