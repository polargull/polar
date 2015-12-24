package com.polarbear.service.product.create;

import com.polarbear.domain.Product;

public interface IProductCreater {

	public Product create(Product product, Object... param);
}
