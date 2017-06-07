package com.andcup.hades.httpserver;

import com.andcup.hades.httpserver.bind.Controller;
import com.andcup.hades.httpserver.bind.Request;
import com.andcup.hades.httpserver.utils.LogUtils;
import com.andcup.hades.httpserver.utils.ScanForClasses;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/15 14:42.
 * Description:
 */
class HadesAnnotationLoader {

    public Map<String, RequestInvoker> loadMethod(String packageName)  {

        LogUtils.info(HadesAnnotationLoader.class,packageName);

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
                                LogUtils.info(HadesAnnotationLoader.class," Request method : " + invoker.path + " return type must be HadesInvokeResponse");
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
                LogUtils.info(HadesAnnotationLoader.class," init consumer class error : " + e.getMessage());
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
