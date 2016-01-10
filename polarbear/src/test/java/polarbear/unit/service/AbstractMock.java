package polarbear.unit.service;

import java.lang.reflect.Field;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import polarbear.test.util.TestAnnotationFiledUtil;

import com.polarbear.dao.BaseDao;

public abstract class AbstractMock {
    protected final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    public BaseDao userDao, productDao, shopcartDao, shopcartLogDao;
    
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private void dynamicSetCompomentMock(String fieldName, Object mock) {
        try {
            Field name = getClass().getField(fieldName);
            name.set(this, mock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class getFieldType(String fieldName) {
        try {
            Field name = getClass().getField(fieldName);
            return name.getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void setServiceAndDependentComponent(Object service, String... compomentNames) {
        TestAnnotationFiledUtil addFiledUtil = new TestAnnotationFiledUtil().setComponent(service);
        for (String daoName : compomentNames) {
            Object mock = context.mock(getFieldType(daoName), daoName);
            dynamicSetCompomentMock(daoName, mock);
            addFiledUtil.addField(daoName, mock);
        }
    }
}
