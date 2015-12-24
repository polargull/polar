package com.polarbear.service.product.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polarbear.dao.BaseDao;
import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;

@Service("multiStyleCreater")
public class MultiStyleCreater implements IProductCreater {
	@Autowired
	private BaseDao<ProductStyle> styleDao;
	@Autowired
	private BaseDao<Product> productDao;

	public Product create(Product product, Object... param) {
		ProductStyle style = (ProductStyle) param[0];
		styleDao.store(style);
		product.setProductStyle(style);
		productDao.store(product);
		return product;
	}

}
