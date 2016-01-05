package polarbear.acceptance;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.SAXException;

import polarbear.test.util.JsonResultConvertUtil;

import com.alibaba.fastjson.JSON;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.polarbear.util.JsonResult;

public class Request {
    String url;
    WebRequest req;
    Map<String, String> reqParam = new HashMap<String, String>();
    WebConversation mockExplore = new WebConversation();

    private Request(String url) {
        this.url = url;
        HttpUnitOptions.setScriptingEnabled(false);
        HttpUnitOptions.setDefaultCharacterSet("utf-8");
    }

    public static Request anRequest(String url) {
        return new Request(url);
    }

    public void withScripting() {
        HttpUnitOptions.setScriptingEnabled(true);
    }

    public Request withCookie(String name, String value) {
        mockExplore.putCookie(name, value);
        return this;
    }

    public Request addParams(String name, String value) {
        reqParam.put(name, value);
        return this;
    }

    public void post(ResultCallback callback) {
        req = new PostMethodWebRequest(url);        
        attachRequestParam();
        try {
            handleReq(callback);
        } catch (SAXException e) {
            callback.onError(e);
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    private void handleReq(ResultCallback callback) throws IOException, SAXException {
        WebResponse response = mockExplore.getResponse(req);
        JsonResult jsonResult = JsonResultConvertUtil.convertJsonObj(response.getText());
        callback.getMockExplore(mockExplore);
        callback.onSuccess(jsonResult);
        callback.onResponse(response);
    }

    public void get(ResultCallback callback) {
        req = new GetMethodWebRequest(url);
        attachRequestParam();
        try {
            handleReq(callback);
        } catch (SAXException e) {
            callback.onError(e);
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    private void attachRequestParam() {
        Iterator<String> it = reqParam.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            req.setParameter(name, reqParam.get(name));
        }
    }

    /**
     * 回调基类
     * 
     * @param <T>
     */
    public static abstract class ResultCallback {
        /**
         * 错误回调入口
         * 
         * @param volleyError
         */
        public void onError(Exception error) {
            fail("网络错误或服务器异常, e:" + error);
        }

        public void onResponse(WebResponse response) {

        }

        public void getMockExplore(WebConversation mockExplore) {
            
        }
        /**
         * 成功回调入口
         * 
         * @param t
         */
        public abstract void onSuccess(JsonResult jsonResult) throws UnsupportedEncodingException;

    }
    // WebRequest req = new PostMethodWebRequest(url);
    // req.setParameter("email", "fuwei04ruanjian@126.com");
    // req.setParameter("password", "123456");
    // WebResponse rep = session.getResponse(req);
    // JsonResult msgInfo = JSON.parseObject(rep.getText(), JsonResult.class);
    // System.out.println(msgInfo.getBody().get("UserVo").toString());
}
