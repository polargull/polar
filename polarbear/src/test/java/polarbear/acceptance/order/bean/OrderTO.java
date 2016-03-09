package polarbear.acceptance.order.bean;

import java.util.List;

import com.polarbear.domain.Order;
import com.polarbear.domain.OrderList;

public class OrderTO {
    Order order;
    List<OrderList> subOrderList;

    public OrderTO() {
    }

    public OrderTO(Order order, List<OrderList> subOrderList) {
        super();
        this.order = order;
        this.subOrderList = subOrderList;
    }

    public Order getOrder() {
        return order;
    }

    public List<OrderList> getSubOrderList() {
        return subOrderList;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setSubOrderList(List<OrderList> subOrderList) {
        this.subOrderList = subOrderList;
    }

}