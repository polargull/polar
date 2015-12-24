package temp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.polarbear.service.product.query.bean.ProductStyleProperty;

public class FastJsonTest {
    @Test
    public void testJsonStrToJsonObject() {
        // // ProductStyleProperty
        String text = "{\"name\":\"颜色\", \"value\":\"黄色\"}";
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
    }

}
