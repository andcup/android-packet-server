package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Controller;
import com.andcup.hades.hts.server.bind.Request;
import com.andcup.hades.hts.server.utils.ScanForClasses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Amos
 * Date : 2017/5/15 14:42.
 * Description:
 */
class HadesAnnotationLoader {

    final static Logger sLogger = LoggerFactory.getLogger(HadesAnnotationLoader.class);

    public Map<String, RequestInvoker> loadMethod(String packageName)  {

        sLogger.info(packageName);

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
                sLogger.error(" init consumer class error : " + e.getMessage());
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
            if(type.equals("file")){
                return ScanForClasses.getClassNameByFile(url.getPath(), null, true);
            }else if(type.equals("jar")){
                return ScanForClasses.getClassNameByJar(url.getPath(), true);
            }
        }
        return null;
    }
}
