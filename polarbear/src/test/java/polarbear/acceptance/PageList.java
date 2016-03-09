package polarbear.acceptance;

import java.util.List;

public class PageList<T> {
    int total;
    int pageNo;
    int pageTotal;
    List<T> list;

    public PageList() {
    }

    public int getTotal() {
        return total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public List<T> getList() {
        return list;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}