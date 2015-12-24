package temp;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.polarbear.util.JsonResult;

public class HttpUnitTest {
    String url;
    WebConversation session;

    @Before
    public void setUp() throws Exception {
        url = "http://localhost:8080/login.json";
        assertNotNull("server.url not set", url);
        HttpUnitOptions.setScriptingEnabled(false);
        session = new WebConversation();
    }

    public void testGetHtmlContent() throws MalformedURLException, IOException, SAXException {
        System.out.println("直接获取网页内容：");
        WebResponse wr = session.getResponse(url);
        System.out.println(wr.getText());
    }

    public void testGetJsonContent() throws MalformedURLException, IOException, SAXException {
        System.out.println("直接获取json内容：");
        WebResponse rep = session.getResponse(url);
        JSONObject json = JSON.parseObject(rep.getText());
        System.out.println(json);
    }

    @Test
    public void shouldInvalidateWhenInputErrNameOrPwd() throws MalformedURLException, IOException, SAXException {
        // 向指定的URL发出请求
        WebRequest req = new PostMethodWebRequest(url);
        req.setParameter("uname", "极地鸥");
        req.setParameter("password", "123abc");
        WebResponse rep = session.getResponse(req);
    }
}
