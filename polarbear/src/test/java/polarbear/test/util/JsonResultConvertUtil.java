package polarbear.test.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

import org.springframework.test.web.servlet.MvcResult;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.polarbear.util.JsonResult;
import com.polarbear.util.Constants.ResultState;

public class JsonResultConvertUtil {
    public static ResultState resultState(JsonResult jsonResult) throws UnsupportedEncodingException {
        ResultState[] states = ResultState.values();
        for (ResultState state : states) {
            if (jsonResult.getHead().getEcode().equals(state.ecode())) {
                return state;
            }
        }
        throw new UnsupportedEncodingException("编码结果错误");
    }

    public static ResultState resultState(MvcResult result) throws UnsupportedEncodingException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultState(jsonResult);
    }

    public static String resultBody(JsonResult jsonResult, String bodyName) throws UnsupportedEncodingException {
        if (jsonResult.getBody().get(bodyName) == null) {
            return null;
        }
        return jsonResult.getBody().get(bodyName).toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        JsonResult jsonResult = new JsonResult();
        jsonResult.put("cellphone", "13717686218");
        System.out.println(resultBody(jsonResult, "cellphone"));
    }

    public static <T> T resultBody(JsonResult jsonResult, Class<T> clazz) throws UnsupportedEncodingException {
        if (jsonResult.getBody().get(clazz.getSimpleName().toLowerCase()) == null) {
            return null;
        }
        return (T) JSON.parseObject(jsonResult.getBody().get(clazz.getSimpleName().toLowerCase()).toString(), clazz);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T resultBodyArray(JsonResult jsonResult, TypeReference<T> type) throws UnsupportedEncodingException, ClassNotFoundException {
        ParameterizedType parameterizedType = (ParameterizedType) type.getClass().getGenericSuperclass();
        ParameterizedTypeImpl typeImpl = (ParameterizedTypeImpl)parameterizedType.getActualTypeArguments()[0];
        String className = typeImpl.getActualTypeArguments()[0].toString().split(" ")[1];
        Class genericeClass = Class.forName(className);
        return (T) JSON.parseArray(jsonResult.getBody().get(typeImpl.getRawType().getSimpleName().toLowerCase()).toString(), genericeClass);
    }

    public static <T> T resultBody(JsonResult jsonResult, TypeReference<T> type) throws UnsupportedEncodingException, ClassNotFoundException {
        ParameterizedType parameterizedType = (ParameterizedType) type.getClass().getGenericSuperclass();
        ParameterizedTypeImpl typeImpl = (ParameterizedTypeImpl)parameterizedType.getActualTypeArguments()[0];
        return (T) JSON.parseObject(jsonResult.getBody().get(typeImpl.getRawType().getSimpleName().toLowerCase()).toString(), type);
    }
    
    public static String resultBody(MvcResult result, String bodyName) throws UnsupportedEncodingException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultBody(jsonResult, bodyName);
    }

    public static <T> T resultBody(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultBody(jsonResult, clazz);
    }
    
    public static <T> T resultBody(MvcResult result, TypeReference<T> type) throws UnsupportedEncodingException, ClassNotFoundException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultBody(jsonResult, type);
    }

    public static JsonResult convertJsonObj(MvcResult result) throws UnsupportedEncodingException {
        return convertJsonObj(result.getResponse().getContentAsString());
    }

    public static JsonResult convertJsonObj(String json) {
        return JSON.parseObject(json, JsonResult.class);
    }

    private static String convertJsonStr(JsonResult jsonResult) {
        return JSON.toJSONString(jsonResult);
    }
}
