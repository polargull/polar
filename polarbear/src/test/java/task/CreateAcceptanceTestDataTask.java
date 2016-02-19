package task;

import static polarbear.testdata.DomainEntityConvertSqlUtil.createInsertSql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import polarbear.testdata.acceptance.testdata.AdminAcceptanceTestDataFactory;
import polarbear.testdata.acceptance.testdata.CategoryAcceptanceTestDataFactory;
import polarbear.testdata.acceptance.testdata.ProductAcceptanceTestDataFactory;
import polarbear.testdata.acceptance.testdata.ProductStyleAcceptanceTestDataFactory;
import polarbear.testdata.acceptance.testdata.UserAcceptanceTestDataFactory;

public class CreateAcceptanceTestDataTask {
    static StringBuilder sqlSb;
    public static void main(String[] args) throws IOException, SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        sqlSb = new StringBuilder();        
        createDomainSql(AdminAcceptanceTestDataFactory.class);
        createDomainSql(UserAcceptanceTestDataFactory.class);
        createDomainSql(CategoryAcceptanceTestDataFactory.class);
        createDomainSql(ProductStyleAcceptanceTestDataFactory.class);
        createDomainSql(ProductAcceptanceTestDataFactory.class);
        //"C:/Users/haohao/git/polar/polarbear/schemes_insert_testdata.sql"
        wirteSqlFile(args[0]);
    }

    private static void wirteSqlFile(String sqlFilePath) throws IOException, SQLException {
        File sqlFile = new File(sqlFilePath);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sqlFile), "UTF-8");
        Writer writer = new BufferedWriter(out);
        writer.write(sqlSb.toString());
        writer.flush();
        writer.close();
    }

    private static StringBuilder createDomainSql(Class domainFactory) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
        StringBuilder domainSql = new StringBuilder();
        Method[] methods = domainFactory.getMethods();
        for (Method method : methods) {
            if (!method.getName().startsWith("create")) {
                continue;
            }
            Object domain = method.invoke(null);
            domainSql.append(createInsertSql(domain)).append(";").append("\n");
        }
        
        return sqlSb.append(domainSql);
    }
}