package polarbear.testdata.acceptance.testdata;

import com.polarbear.domain.Category;

public class CategoryAcceptanceTestDataFactory {
    public static Category createCategory1() {
        return new Category(1l, "服装");
    }
    
    public static Category createCategory2() {
        return new Category(2l, "化妆品");
    }
    
    public static Category createCategory3() {
        return new Category(3l, "玩具");
    }
}
