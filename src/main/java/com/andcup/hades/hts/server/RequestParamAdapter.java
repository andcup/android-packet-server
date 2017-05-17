package com.andcup.hades.hts.server;

import com.andcup.hades.hts.server.bind.Var;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos
 * Date : 2017/5/15 18:10.
 * Description:
 */
interface RequestParamAdapter {

    List<Object> adapter(RequestInvoker invoker, Map<String, String> values);

    RequestParamAdapter REQUEST = new RequestParamAdapter() {
        public List<Object> adapter(RequestInvoker invoker, Map<String, String> values){
            List<Object> listValue = new ArrayList<Object>();
            Parameter[] parameters = invoker.method.getParameters();
            for(int i=0; i< parameters.length; i++){
                Var var = parameters[i].getAnnotation(Var.class);
                ParamFiller.fill(listValue, parameters[i].getType(), values.get(var.value()));
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
