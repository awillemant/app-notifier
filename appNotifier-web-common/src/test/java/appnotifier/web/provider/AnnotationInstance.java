package appnotifier.web.provider;

import java.lang.annotation.Annotation;

public class AnnotationInstance implements Annotation {

    private Class<?> annotation;

    public AnnotationInstance(Class<?> annotation) {
        this.annotation = annotation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Annotation> annotationType() {
        return (Class<? extends Annotation>) annotation;
    }
}