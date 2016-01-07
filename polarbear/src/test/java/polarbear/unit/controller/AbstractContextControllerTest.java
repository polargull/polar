package polarbear.unit.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.lang.reflect.Method;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import polarbear.unit.service.AbstractMock;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.polarbear.web.exception.MVCExceptionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-mvc.xml" })
public class AbstractContextControllerTest extends AbstractMock {
    @Autowired
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    protected MockMvc            mockMvc;

    protected void setUp(Object controllerMock) {
        this.mockMvc = standaloneSetup(controllerMock).setHandlerExceptionResolvers(createExceptionResolver()).setMessageConverters(fastJsonHttpMessageConverter).build();
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(MVCExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new MVCExceptionHandler(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(fastJsonHttpMessageConverter);
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
}
