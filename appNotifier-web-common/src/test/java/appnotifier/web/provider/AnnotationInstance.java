package appnotifier.web.provider;

import java.lang.annotation.Annotation;

public class AnnotationInstance implements Annotation {

    private Class<? extends Annotation> annotation;

    public AnnotationInstance(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return annotation;
    }

}