package polarbear.acceptance.shopcart.bean;

import com.polarbear.domain.product.Product;

public class ShopcartProduct {
    private Long pid;
    private String name;
    private String img;
    private Integer num;
    private Double price;

    public ShopcartProduct() {
    }
    
    public ShopcartProduct(Product p, int num) {
        this.num = num;
        this.pid = p.getId();
        this.name = p.getName();
        this.img = p.getImage().split(";")[0];
        this.price = p.getRealPrice();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}