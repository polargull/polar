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

import polarbear.testdata.acceptance.testdata.*;

public class CreateAcceptanceTestDataTask {
    public static void main(String[] args) throws IOException, SQLException, Exception{
        String testPath = "C:/Users/haohao/git/polar/polarbear/schemes_insert_testdata.sql";
        wirteSqlFile(testPath, createAllTestDataScript());
        
//        wirteSqlFile(args[0], createAllTestDataScript());
    }

    public static String createAllTestDataScript() throws Exception {
        StringBuilder domainsSql = new StringBuilder();
        try {
            domainsSql.append(createDomainSql(AdminAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(UserAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(AddressAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(CategoryAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(ProductStyleAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(ProductAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(OrderAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(OrderListAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(ShopcartAcceptanceTestDataFactory.class));
            domainsSql.append(createDomainSql(ShopcartDetailAcceptanceTestDataFactory.class));
        } catch (Exception e) {
            throw new Exception("实体对象转换sql脚本错误!");
        }
        return domainsSql.toString();
    }

    public static String[] createAllTestDataScriptArray() throws Exception {
        return createAllTestDataScript().split(";\n");
    }

    private static void wirteSqlFile(String sqlFilePath, String sqlScript) throws IOException, SQLException {
        File sqlFile = new File(sqlFilePath);
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sqlFile), "UTF-8");
        Writer writer = new BufferedWriter(out);
        writer.write(sqlScript);
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
        return domainSql;
    }
}