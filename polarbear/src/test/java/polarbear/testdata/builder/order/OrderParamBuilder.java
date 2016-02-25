package polarbear.testdata.builder.order;

import com.polarbear.domain.Address;
import com.polarbear.service.balance.to.BuyProduct;
import com.polarbear.service.order.bean.OrderParam;
import com.polarbear.util.Constants.BUY_MODE;
import com.polarbear.util.Constants.PAY_CODE;

public class OrderParamBuilder {
    long[] pids;
    int[] nums;
    Integer buyMode;
    Integer payCode;
    Long addressId;

    Address address;
    BuyProduct[] buyProducts;

    public Address getAddress() {
        return address;
    }

    public BuyProduct[] getBuyProducts() {
        return buyProducts;
    }

    public static OrderParamBuilder anOrderParam() {
        return new OrderParamBuilder();
    }

    public OrderParamBuilder withBuyProducts(BuyProduct[] buyProducts) {
        this.buyProducts = buyProducts;
        pids = new long[buyProducts.length];
        nums = new int[buyProducts.length];
        for (int i = 0; i < buyProducts.length; i++) {
            pids[i] = buyProducts[i].getPid();
            nums[i] = buyProducts[i].getBuyNum();
        }
        return this;
    }

    public OrderParamBuilder withBuyMode(BUY_MODE buyMode) {
        this.buyMode = buyMode.value();
        return this;
    }

    public OrderParamBuilder withPayCode(PAY_CODE payCode) {
        this.payCode = payCode.value();
        return this;
    }

    public OrderParamBuilder withAddress(Address address) {
        this.address = address;
        this.addressId = address.getId();
        return this;
    }

    public OrderParam build() {
        return new OrderParam(pids, nums, buyMode, payCode, addressId);
    }
}