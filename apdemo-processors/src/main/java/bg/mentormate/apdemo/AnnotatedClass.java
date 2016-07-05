package bg.mentormate.apdemo;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class AnnotatedClass {
    private final String name;
    private final TypeElement typeElement;
    private final List<VariableElement> views;
    private final int layoutId;

    public AnnotatedClass(TypeElement typeElement, List<VariableElement> views) {
        this.name = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        this.layoutId = typeElement.getAnnotation(FragmentBuilder.class).value();
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

    public int getLayoutId() {
        return layoutId;
    }

    public List<VariableElement> getViews() {
        return views;
    }
}
