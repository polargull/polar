package com.polarbear.service.product.query;

import com.polarbear.domain.Product;
import com.polarbear.service.product.query.bean.NeedStyle;

public interface IProductQuery {
	public Product querySameStyleProductByNeedStyle(NeedStyle needStyle);
}
