package com.polarbear.web.product.back;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Category;
import com.polarbear.domain.Product;
import com.polarbear.service.PageList;
import com.polarbear.util.JsonEasyUiResult;

@Controller
@RequestMapping("/back")
public class ProductManagerController {
    private Log log = LogFactory.getLog(ProductManagerController.class);
    @Autowired(required = false)
    private BaseDao<Product> productDao;

    @RequestMapping(value = { "/productList.do", "/productList.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object productList() throws ValidateException, DaoException {
        PageList<Product> pageList = productDao.findByNamedQueryByPage("queryPutOnProductByCategoryId", "1", null, new Category(1L));        
        return new JsonEasyUiResult<Product>(pageList);
    }
}