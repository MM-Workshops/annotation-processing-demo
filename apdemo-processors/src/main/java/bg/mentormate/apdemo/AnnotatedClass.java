package bg.mentormate.apdemo;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class AnnotatedClass {
    public final String annotatedClassName;
    public final TypeElement typeElement;

    public AnnotatedClass(TypeElement typeElement) {
        this.annotatedClassName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }
}
