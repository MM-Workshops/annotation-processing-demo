package bg.mentormate.apdemo;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class FragmentAnnotatedClass {
    private final String name;
    private final TypeElement typeElement;
    private final List<VariableElement> views;
    private final List<ExecutableElement> clickMethods;
    private final List<ExecutableElement> lifeCycleMethods;
    private final TypeElement screenListener;
    private final int layoutId;

    public FragmentAnnotatedClass(TypeElement typeElement,
                                  List<VariableElement> views,
                                  List<ExecutableElement> clickMethods,
                                  List<ExecutableElement> lifeCycleMethods, TypeElement screenListener) {
        this.name = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        this.layoutId = typeElement.getAnnotation(Screen.class).value();
        this.views = views;
        this.clickMethods = clickMethods;
        this.lifeCycleMethods = lifeCycleMethods;
        this.screenListener = screenListener;
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

    public List<ExecutableElement> getClickMethods() {
        return clickMethods;
    }

    public List<ExecutableElement> getLifeCycleMethods() {
        return lifeCycleMethods;
    }

    public TypeElement getScreenListener() {
        return screenListener;
    }
}
