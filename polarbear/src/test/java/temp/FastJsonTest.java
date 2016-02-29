package temp;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.polarbear.domain.User;
import com.polarbear.service.login.bean.LoginData;
import com.polarbear.service.product.query.bean.ProductStyleProperty;

public class FastJsonTest {
    @Test
    public void testJsonStrToJsonObject() {
        // // ProductStyleProperty
        String text = "{\"msg_id\": 3303310512, \"error\": {\"message\": \"cannot find user by this audience\", \"code\": 1011}}";
        /** 将JSON字符串转换为JSON对象 **/
        JSONObject json = JSON.parseObject(text);
        System.out.println(json);
    }

    @Test
    public void testJsonStrToDomainObject() {
        String text = "{\"name\":\"颜色\", \"value\":\"黄色\"}";
        /** 将JSON字符串转换为JavaBean对象 **/
        ProductStyleProperty property = JSON.parseObject(text, ProductStyleProperty.class);
        System.out.println(property.getName() + " " + property.getValue());
    }
    
    @Test
    public void testJsonStrToGenericDomainObject() {
        String text = "{\"user\":{\"id\":1,\"createTime\":1454386542,\"cellphone\":13717686218,\"name\":\"极地鸥\"}}";
        /** 将JSON字符串转换为JavaBean对象 **/
        LoginData<User> loginData = JSON.parseObject(text, new TypeReference<LoginData<User>>(){});
        System.out.println(loginData.getUser().getName());
    }

    @Test
    public void testJsonArrayStrStrToList() {
        String textlist = "[{\"name\":\"颜色\",\"values\":[\"红色\",\"黄色\"]},{\"name\":\"大小\",\"values\":[\"M\",\"L\",\"XL\"]}]";
        List<ProductStyleProperty> productStylePropertyList = JSONArray.parseArray(textlist, ProductStyleProperty.class);
        for (ProductStyleProperty p : productStylePropertyList) {
            System.out.println(p.getName()+" "+p.getValues()[0]);
        }
    }

    @Test
    public void testListToJsonArrayStr() {
        ProductStyleProperty colorProperty = new ProductStyleProperty();
        colorProperty.setName("颜色").setValues(new String[] { "红色", "黄色" });
        ProductStyleProperty sizeProperty = new ProductStyleProperty();
        sizeProperty.setName("大小").setValues(new String[] { "M", "L", "XL" });
        List<ProductStyleProperty> stylePropertyList = new ArrayList<ProductStyleProperty>();
        stylePropertyList.add(colorProperty);
        stylePropertyList.add(sizeProperty);
        String jsonObject = JSON.toJSONString(stylePropertyList);
        System.out.println(jsonObject);
        System.out.println(new Integer(2).equals(1));
    }
    
    static class Foo<T>{
        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    @Test
    public void test_FirstWithClass() {
        Foo<List<Integer>> foo = new Foo<List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(3);
        list.add(1);
        foo.setT(list);
        String v = JSON.toJSONString(foo);
        System.out.println(v);
        //parse with class
        Foo<?> rst = JSON.parseObject(v, foo.getClass());
        System.out.println(rst.getT());
        //parse with TypeReference
        System.out.println(new TypeReference<Foo<List<Integer>>>(){}.getClass().getGenericSuperclass().toString());
        ParameterizedType parameterizedType = (ParameterizedType) new TypeReference<Foo<List<Integer>>>(){}.getClass().getGenericSuperclass();
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
        rst = JSON.parseObject(v,new TypeReference<Foo<List<Integer>>>(){});
        List lst = (List)rst.getT();
        System.out.println(lst.get(1));
    }
    
//  @Test//此用例跟上边那个不能同时跑，要不然上边跑过之后下边就跑不通了
    public void test_FirstWithTypeReference() {
        Foo<List<Integer>> foo = new Foo<List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(3);
        foo.setT(list);
        String v = JSON.toJSONString(foo);
        System.out.println(v);
        //parse with TypeReference
//        Foo<?> rst = JSON.parseObject(v,new TypeReference<Foo<List<Integer>>>(){});
    }

}
