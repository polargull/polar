package polarbear.acceptance.shopcart;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import polarbear.acceptance.shopcart.bean.MyShopcart;
import polarbear.acceptance.shopcart.bean.ShopcartProduct;

import com.polarbear.domain.product.Product;

public class TestMyShopcartUtil {

    public static int getShopcartTotalNum(List<ShopcartProduct> shopcartDataList) {
        int totalNum = 0;
        for (ShopcartProduct sp : shopcartDataList) {
            totalNum += sp.getNum();
        }
        return totalNum;
    }

    public static double getShopcartTotalPrice(List<ShopcartProduct> shopcartDataList) {
        double totalPrice = 0;
        for (ShopcartProduct sp : shopcartDataList) {
            totalPrice += sp.getPrice() * sp.getNum();
        }
        return totalPrice;
    }

    public static List<ShopcartProduct> addShopcartDataList(final List<ShopcartProduct> currentShopcartDataList, final ShopcartProduct sp) {
        Set<Long> pidSet = new HashSet<Long>();
        for (ShopcartProduct shopcartProduct : currentShopcartDataList) {
            pidSet.add(shopcartProduct.getPid());
        }
        if (!pidSet.contains(sp.getPid())) {
            currentShopcartDataList.add(sp);
            return currentShopcartDataList;
        }
        for (ShopcartProduct shopcartProduct : currentShopcartDataList) {
            if (shopcartProduct.getPid().equals(sp.getPid())) {
                shopcartProduct.setNum(shopcartProduct.getNum() + sp.getNum());
                break;
            }
        }
        return currentShopcartDataList;
    }

    public static List<ShopcartProduct> updateShopcartDataList(final List<ShopcartProduct> currentShopcartDataList, final ShopcartProduct sp) {
        for (ShopcartProduct shopcartProduct : currentShopcartDataList) {
            if (shopcartProduct.getPid().equals(sp.getPid())) {
                shopcartProduct.setNum(sp.getNum());
                break;
            }
        }
        return currentShopcartDataList;
    }

    public static List<ShopcartProduct> removeProductInShopcartDataList(final List<ShopcartProduct> currentShopcartDataList, final Product p) {        
        for (ShopcartProduct shopcartProduct : currentShopcartDataList) {
            if (shopcartProduct.getPid().equals(p.getId())) {
                currentShopcartDataList.remove(shopcartProduct);
                break;
            }
        }
        return currentShopcartDataList;
    }

    public static void assertThatMyShopcart(MyShopcart shopcart, List<ShopcartProduct> shopcartDataList) {
        assertThat("商品种类数:", shopcart.getProductList().size(), equalTo(shopcartDataList.size()));
        assertThat("购买的商品总数:", shopcart.getProductNum(), equalTo(getShopcartTotalNum(shopcartDataList)));
        assertThat("总价格:", shopcart.getTotalPrice(), equalTo(getShopcartTotalPrice(shopcartDataList)));
        for (ShopcartProduct sp : shopcart.getProductList()) {
            assertThat("商品图片:", sp.getImg(), startsWith("http"));
            assertThat("商品名称:", sp.getName(), not(isEmptyString()));
            assertThat("商品购买数:", sp.getNum(), greaterThan(0));
            assertThat("商品id:", sp.getPid(), greaterThan(0l));
            assertThat("商品价格:", sp.getPrice(), greaterThan(0d));
        }
    }
    
    public static String convertRequestParamByShopcartProductArray(ShopcartProduct... shopcartProducts) {
        String shopcartCookieData = "";
        for (ShopcartProduct shopcartProduct : shopcartProducts) {
            shopcartCookieData += shopcartProduct.getPid() + "_" + shopcartProduct.getNum() + "|";
        }
        return shopcartCookieData;
    } 
}
