package polarbear.testdata;

import static polarbear.test.util.Constants.CATEGORY_NAME;
import static polarbear.test.util.Constants.PRODUCT_NAME;
import static polarbear.test.util.Constants.PRODUCT_STYLE;
import static polarbear.test.util.Constants.PRODUCT_STYLE_ID;
import static polarbear.test.util.Constants.SHOPCARD_ID;
import static polarbear.testdata.builder.product.ProductBuilder.anProduct;
import static polarbear.testdata.builder.product.StyleBuilder.anStyle;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import polarbear.testdata.builder.product.StyleBuilder;

import com.polarbear.domain.Category;

public class DomainEntityConvertSqlUtil {
    static Pattern pattern = Pattern.compile("^(com.polarbear.domain).*");

    public static void main(String[] args) throws SQLException {
        StyleBuilder style = anStyle().withId(PRODUCT_STYLE_ID).withProperty(PRODUCT_STYLE);
        Category category = new Category(SHOPCARD_ID, CATEGORY_NAME);
        System.out.println(createInsertSql(anProduct().withID(4l).withName(PRODUCT_NAME + 4).pullOff().withStyle(style).build()));
        System.out.println(createInsertSql(anProduct().withID(1l).withName(PRODUCT_NAME + 1).putOn().withPrice(60d).saleDay(1).salePrice(50d).withCategory(category).build()));
    }

    public static String createInsertSql(Object obj) throws SQLException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder columNameSb = new StringBuilder();
        StringBuilder columValSb = new StringBuilder();
        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (isFilteField(field, obj))
                    continue;
                if (i != 0) {
                    columNameSb.append(", ");
                    columValSb.append(", ");
                }
                columNameSb.append(getColumnName(field));
                columValSb.append(getColumnVal(field, obj));
            }
        } catch (Exception e) {
            throw new SQLException("entity parse sql exception, reason:" + e.getMessage());
        }
        StringBuilder insertSqlSb = new StringBuilder("insert into ");
        insertSqlSb.append(getTableName(obj)).append("(").append(columNameSb.toString()).append(") ");
        insertSqlSb.append("values(").append(columValSb.toString()).append(")");
        return insertSqlSb.toString();
    }

    private static boolean isFilteField(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj) == null;
    }

    private static String getTableName(Object obj) {
        if (obj.getClass().isAnnotationPresent(Table.class)) {
            Table table = obj.getClass().getAnnotation(Table.class);
            return table.name();
        }
        return obj.getClass().getSimpleName();
    }

    private static String getColumnVal(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        StringBuilder columValSb = new StringBuilder();
        if (field.isAnnotationPresent(Embedded.class)) {
            Object embeddedObj = field.get(obj);
            Field[] embeddedFields = field.getType().getDeclaredFields();
            for (int i = 0; i < embeddedFields.length; i++) {
                embeddedFields[i].setAccessible(true);
                columValSb.append("'").append(embeddedFields[i].get(embeddedObj)).append("'");
                if (i != embeddedFields.length - 1)
                    columValSb.append(", ");
            }
            return columValSb.toString();
        }
        if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
            columValSb.append("'").append(field.getType().getMethod("getId").invoke(field.get(obj))).append("'");
            return columValSb.toString();
        }
        columValSb.append("'").append(field.get(obj)).append("'");
        return columValSb.toString();
    }

    private static String getColumnName(Field field) {
        StringBuilder columNameSb = new StringBuilder();
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (StringUtils.isEmpty(column.name())) {
                return columNameSb.append("`").append(field.getName()).append("`").toString();
            }
            return columNameSb.append("`").append(column.name()).append("`").toString();
        }
        if (field.isAnnotationPresent(Embedded.class)) {
            Field[] embeddedFields = field.getType().getDeclaredFields();
            for (int i = 0; i < embeddedFields.length; i++) {
                columNameSb.append("`").append(embeddedFields[i].getName()).append("`");
                if (i != embeddedFields.length - 1)
                    columNameSb.append(",");
            }
            return columNameSb.toString();
        }
        if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
            return columNameSb.append("`").append(field.getName() + "_id").append("`").toString();
        }
        if (field.isAnnotationPresent(Id.class)) {
            return columNameSb.append("`").append(field.getName()).append("`").toString();
        }
        throw new IllegalArgumentException(field.getName() + " column exception!");
    }
}
