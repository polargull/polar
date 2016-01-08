package polarbear.test.util;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSON;
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
        throw new UnsupportedEncodingException();
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

    public static String resultBody(MvcResult result, String bodyName) throws UnsupportedEncodingException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultBody(jsonResult, bodyName);
    }

    public static <T> T resultBody(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        JsonResult jsonResult = convertJsonObj(result);
        return resultBody(jsonResult, clazz);
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
