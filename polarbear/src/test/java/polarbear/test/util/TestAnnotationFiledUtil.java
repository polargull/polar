package polarbear.test.util;

import org.springframework.test.util.ReflectionTestUtils;

public class TestAnnotationFiledUtil {
	public Object component;

	public TestAnnotationFiledUtil setComponent(Object component) {
		this.component = component;
		return this;
	}

	public TestAnnotationFiledUtil addField(String annotationComponentName,
			Object annotationComponent) {
		ReflectionTestUtils.setField(component, annotationComponentName,
				annotationComponent);
		return this;
	}

}
