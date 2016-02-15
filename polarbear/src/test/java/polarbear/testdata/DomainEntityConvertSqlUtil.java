package polarbear.testdata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainEntityConvertSqlUtil {
    static Pattern pattern = Pattern.compile("^(com.polarbear.domain).*");

    private static String createInsertSql(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder columNameSb = new StringBuilder();
        StringBuilder columValSb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (isFilteField(field, obj))continue;
            columNameSb.append(getColumnName(field));
            columValSb.append(getColumnVal(field, obj));
            if (i != fields.length - 1) {
                columNameSb.append(", ");
                columValSb.append(", ");
            }
        }
        StringBuilder insertSqlSb = new StringBuilder("insert into ");
        insertSqlSb.append(obj.getClass().getSimpleName()).append("(").append(columNameSb.toString()).append(") ");
        insertSqlSb.append("values(").append(columValSb.toString()).append(")");
        return insertSqlSb.toString();
    }

    private static boolean isFilteField(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj) == null;
    }

    private static String getColumnVal(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        StringBuilder columValSb = new StringBuilder();
        Matcher matcher = pattern.matcher(field.get(obj).toString());
        if (matcher.find()) {
            columValSb.append("'").append(field.get(obj).getClass().getMethod("getId").invoke(field.get(obj))).append("'");
            return columValSb.toString();
        }
        columValSb.append("'").append(field.get(obj)).append("'");
        return columValSb.toString();
    }

    private static String getColumnName(Field field) {
        StringBuilder columNameSb = new StringBuilder();
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.toString().indexOf("ManyToOne") != -1) {
                return columNameSb.append("`").append(field.getName()).append("_id").append("`").toString();
            }
        }
        return columNameSb.append("`").append(field.getName()).append("`").toString();
    }
}