package com.andcup.hades.hts.server;

import com.andcup.hades.hts.core.tools.JsonConvertTool;
import com.andcup.hades.hts.server.bind.Body;
import com.andcup.hades.hts.server.bind.Var;
import com.andcup.hades.hts.server.utils.IOUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.sun.net.httpserver.HttpExchange;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/15 18:10.
 * Description:
 */
interface RequestParamAdapter {

    List<Object> adapter(RequestInvoker invoker, HttpExchange httpExchange);

    RequestParamAdapter PARAM = new RequestParamAdapter() {
        public List<Object> adapter(RequestInvoker invoker, HttpExchange httpExchange){
            List<Object> listValue = new ArrayList<Object>();
            try {
                Map<String, String>  params = RequestParamsParser.parseUrlParams(httpExchange);
                Parameter[] parameters = invoker.method.getParameters();
                for(int i=0; i< parameters.length; i++){
                    Var var = parameters[i].getAnnotation(Var.class);
                    ParamFiller.fill(listValue, parameters[i].getType(), params.get(var.value()));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return listValue;
        }
    };

    RequestParamAdapter BODY_APP_JSON = new RequestParamAdapter() {
        public List<Object> adapter(RequestInvoker invoker, HttpExchange httpExchange){
            List<Object> listValue = new ArrayList<Object>();
            Parameter[] parameters = invoker.method.getParameters();
            for(int i=0; i< parameters.length; i++){
                Object object = JsonConvertTool.toJson(IOUtils.convertStreamToString(httpExchange.getRequestBody()), parameters[i].getType());
                listValue.add(object);
            }
            return listValue;
        }
    };

    RequestParamAdapter XWWW = new RequestParamAdapter() {
        public List<Object> adapter(RequestInvoker invoker, HttpExchange httpExchange){
            List<Object> listValue = new ArrayList<Object>();
            try {
                Map<String, String>  params = RequestParamsParser.parseXWWWFormUrlEncoded(httpExchange);
                Parameter[] parameters = invoker.method.getParameters();
                for(int i=0; i< parameters.length; i++){
                    Var var = parameters[i].getAnnotation(Var.class);
                    ParamFiller.fill(listValue, parameters[i].getType(), params.get(var.value()));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return listValue;
        }
    };

    /**
     * 参数填充.
     * */
    class ParamFiller{
        public static void fill(List<Object> list, Class<?> parameter, String value) {
            System.out.println(parameter.getTypeName());
            if("java.lang.String".equals(parameter.getTypeName())){
                list.add(value);
            }else if("java.lang.Character".equals(parameter.getTypeName())){
                char[] ch = ((String)value).toCharArray();
                list.add(ch[0]);
            }else if("char".equals(parameter.getTypeName())){
                char[] ch = ((String)value).toCharArray();
                list.add(ch[0]);
            }else if("java.lang.Double".equals(parameter.getTypeName())){
                list.add(Double.parseDouble((String) value));
            }else if("double".equals(parameter.getTypeName())){
                list.add(Double.parseDouble((String) value));
            }else if("java.lang.Integer".equals(parameter.getTypeName())){
                list.add(Integer.parseInt((String) value));
            }else if("int".equals(parameter.getTypeName())){
                list.add(Integer.parseInt((String) value));
            }else if("java.lang.Long".equals(parameter.getTypeName())){
                list.add(Long.parseLong((String) value));
            }else if("long".equals(parameter.getTypeName())){
                list.add(Long.parseLong((String) value));
            }else if("java.lang.Float".equals(parameter.getTypeName())){
                list.add(Float.parseFloat((String) value));
            }else if("float".equals(parameter.getTypeName())){
                list.add(Float.parseFloat((String) value));
            }else if("java.lang.Short".equals(parameter.getTypeName())){
                list.add(Short.parseShort((String) value));
            }else if("shrot".equals(parameter.getTypeName())){
                list.add(Short.parseShort((String) value));
            }else if("java.lang.Byte".equals(parameter.getTypeName())){
                list.add(Byte.parseByte((String) value));
            }else if("byte".equals(parameter.getTypeName())){
                list.add(Byte.parseByte((String) value));
            }else if("java.lang.Boolean".equals(parameter.getTypeName())){
                if("false".equals(value) || "0".equals(value)){
                    list.add(false);
                }else if("true".equals(value) || "1".equals(value)){
                    list.add(true);
                }
            }else if("boolean".equals(parameter.getTypeName())){
                if("false".equals(value) || "0".equals(value)){
                    list.add(false);
                }else if("true".equals(value) || "1".equals(value)){
                    list.add(true);
                }
            }
        }
    }
}
