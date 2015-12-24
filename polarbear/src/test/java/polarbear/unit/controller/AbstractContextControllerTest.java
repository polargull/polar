package polarbear.unit.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import polarbear.unit.service.AbstractMock;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/spring-mvc.xml" })
public class AbstractContextControllerTest extends AbstractMock {
    @Autowired
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter;
    protected MockMvc mockMvc;

    protected void setUp(Object controllerMock) {
        this.mockMvc = standaloneSetup(controllerMock).setMessageConverters(fastJsonHttpMessageConverter).build();
    }
}
