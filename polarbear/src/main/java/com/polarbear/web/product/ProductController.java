package com.polarbear.web.product;

import static com.polarbear.util.Constants.ResultState.PARAM_ERR;
import static com.polarbear.util.Constants.ResultState.SUCCESS;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polarbear.NullObjectException;
import com.polarbear.ValidateException;
import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.dao.PageList;
import com.polarbear.domain.Category;
import com.polarbear.domain.Product;
import com.polarbear.service.product.query.MultipleStyleProductQuery;
import com.polarbear.service.product.query.bean.NeedStyle;
import com.polarbear.util.JsonResult;

@Controller
@RequestMapping("/product")
public class ProductController {
    private Log log = LogFactory.getLog(ProductController.class);
    @Autowired(required = false)
    private BaseDao<Product> productDao;
    @Autowired(required = false)
    private MultipleStyleProductQuery multipleStyleProductQuery;

    @RequestMapping(value = { "/productDetail.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object getProduct(@RequestParam("pid") String pid) throws ValidateException, DaoException {
        log.debug("pid=" + pid);
        validateId(pid);
        Product product = productDao.findById(Product.class, Long.parseLong(pid));
        log.debug("pid = " + pid + ", op successful!");
        return new JsonResult(SUCCESS).put(product);
    }

    @RequestMapping(value = { "/queryMultiplyStyleProduct.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object queryMultiplyStyleProduct(@RequestParam("styleId") String styleId, @RequestParam("property") String property) throws ValidateException, DaoException,
            NullObjectException {
        log.debug("styleId=" + styleId);
        validateId(styleId);
        validateProductProperty(property);
        Product product = multipleStyleProductQuery.querySameStyleProductByNeedStyle(new NeedStyle(Long.parseLong(styleId), property));
        log.debug("styleId = " + styleId + ", op successful!");
        return new JsonResult(SUCCESS).put(product);
    }

    @RequestMapping(value = { "/queryProductByCategory.json" }, method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object queryProductByCategory(@RequestParam("categoryId") String categoryId, @RequestParam("pageNo") String pageNo,
            @RequestParam(required = false, value = "pageSize") String pageSize)
            throws ValidateException, DaoException {
        log.debug("categoryId=" + categoryId);
        validateId(categoryId);
        validateId(pageNo);
        PageList<Product> productList = productDao.findByNamedQueryByPage("queryPutOnProductByCategoryId", pageNo, pageSize, new Category(Long.valueOf(categoryId)));
        log.debug("categoryId=" + categoryId + ", op successful!");
        return new JsonResult(SUCCESS).put(productList.getList());
    }

    private void validateId(String digits) throws ValidateException {
        if (!NumberUtils.isDigits(digits)) {
            throw new ValidateException(PARAM_ERR);
        }
    }

    private void validateProductProperty(String property) throws ValidateException {
        if (StringUtils.isEmpty(property)) {
            throw new ValidateException(PARAM_ERR);
        }
    }
}