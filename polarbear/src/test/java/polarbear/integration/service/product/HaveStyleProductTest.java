package polarbear.integration.service.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static polarbear.testdata.builder.product.StyleBuilder.anStyle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.polarbear.dao.BaseDao;
import com.polarbear.dao.DaoException;
import com.polarbear.domain.ProductStyle;
import com.polarbear.domain.product.Product;

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
		testStyle = anStyle().withProperty("{color:[\"红色\",\"黄色\"],size:[\"M\",\"L\"]}").build();
		productStyleDao.store(testStyle);

		testProduct = new Product();
		testProduct.setName("test1");
		testProduct.setNum(10);
		testProduct.setProductStyle(testStyle);
		testProduct.setCreateTime(11111);
		testProduct.setExtProperty("{color:\"红色\",size:\"M\"}");
		testProduct.setImage("http://imag.myshop.com/2015/mm.jpg;");
		testProduct.setPrice(20d);
		testProduct.setState(1);
		testProduct.setDesc(null);
		testProduct.setTag("衣服");
//		testProduct.setSalePrice(1d);
//		testProduct.setSaleBeginTime(11);
//		testProduct.setSaleEndTime(11);
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
	public void testProductForeignKey() {
		long stylekey = jdbcTemplate.queryForObject(
				"select productStyle_id from product where id = ? ",
				new Object[] { testProduct.getId() }, Long.class);
		assertEquals(stylekey, testStyle.getId());
	}
}
