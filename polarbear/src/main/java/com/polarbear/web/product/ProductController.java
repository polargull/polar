package com.polarbear.web.product;

import static com.polarbear.util.Constants.ResultState.*;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/product")
public class ProductController {
    private Log log = LogFactory.getLog(ProductController.class);
    @Autowired(required = false)
    private BaseDao<Product> productDao;

    @RequestMapping(value = { "/productDetail.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object getProduct(@RequestParam("pid") String pid) throws ValidateException, DaoException {
        log.debug("pid=" + pid);
        validate(pid);
        Product product = productDao.findById(Product.class, Long.parseLong(pid));
        log.debug("pid = " + pid + ", op successful!");
        return new JsonResult(SUCCESS).put(product);
    }

    private void validate(String digits) throws ValidateException {
        if (!NumberUtils.isDigits(digits)) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}