package bg.mentormate.apdemo;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class AnnotatedClass {
    private final String name;
    private final TypeElement typeElement;
    private final List<String> views;

    public AnnotatedClass(TypeElement typeElement, List<String> views) {
        this.name = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        this.views = views;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return typeElement.asType();
    }

    public List<String> getViews() {
        return views;
    }
}
