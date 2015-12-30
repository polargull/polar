package polarbear.integration.service.product;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.Product;
import com.polarbear.domain.ProductStyle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml", "/spring/spring-dao.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class HaveStyleProductTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private ProductStyle testStyle;
	private Product testProduct;
	@Autowired
	private BaseDao<Product> productDao;
	@Autowired
	private BaseDao<ProductStyle> productStyleDao;

	@Before
	public void init() throws DaoException {
		testStyle = new ProductStyle();
		testStyle
				.setStyleProperties("{color:[\"红色\",\"黄色\"],size:[\"M\",\"L\"]}");
		productStyleDao.store(testStyle);

		testProduct = new Product();
		testProduct.setName("test1");
		testProduct.setCount(10);
		testProduct.setProductStyle(testStyle);
		testProduct.setCreateTime(11111);
		testProduct.setExtProperty("{color:\"红色\",size:\"M\"}");
		testProduct.setImage("http://imag.myshop.com/2015/mm.jpg;");
		testProduct.setPrice(20d);
		testProduct.setState(1);
		testProduct.setDesc(null);
		testProduct.setTag("衣服");
		testProduct.setSalePrice(1d);
		testProduct.setSaleBeginTime(11);
		testProduct.setSaleEndTime(11);
		productDao.store(testProduct);
	}

	@Test
	public void testProductStyle() {
		ProductStyle style = jdbcTemplate.queryForObject(
				"select * from product_style where id = ? ",
				new Object[] { testStyle.getId() }, BeanPropertyRowMapper
						.newInstance(ProductStyle.class));
		assertTrue("style property isEquals:", style.toString().equals(
				testStyle.toString()));
	}

	@Test
	public void testProduct() {
		Product p = jdbcTemplate.queryForObject(
				"select * from product where id = ? ",
				new Object[] { testProduct.getId() }, BeanPropertyRowMapper
						.newInstance(Product.class));
		assertTrue(p.toString().equals(testProduct.toString()));
	}

	@Test
	public void testProductForeignKey() {
		long stylekey = jdbcTemplate.queryForObject(
				"select productStyle_id from product where id = ? ",
				new Object[] { testProduct.getId() }, Long.class);
		System.out.println(stylekey+" "+testStyle.getId());
		assertEquals(stylekey, testStyle.getId());
	}
}
