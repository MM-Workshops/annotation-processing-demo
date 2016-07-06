package bg.mentormate.apdemo;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class FragmentCodeGenerator {
    private static final ClassName ACTIVITY_CLASS = ClassName.get("android.app", "Activity");
    private static final ClassName FRAGMENT_CLASS = ClassName.get("android.support.v4.app", "Fragment");
    private static final ClassName LAYOUT_INFLATER_CLASS = ClassName.get("android.view", "LayoutInflater");
    private static final ClassName VIEW_GROUP_CLASS = ClassName.get("android.view", "ViewGroup");
    private static final ClassName BUNDLE_CLASS = ClassName.get("android.os", "Bundle");
    private static final ClassName VIEW_CLASS = ClassName.get("android.view", "View");
    private static final ClassName LOG_CLASS = ClassName.get("android.util", "Log");

    private static final String SCREEN_VARIABLE = "screen";
    private static final String SCREEN_LISTENER_VARIABLE = "screenListener";

    private static final String ON_CREATE_METHOD = "onCreate";
    private static final String ON_CREATE_VIEW_METHOD = "onCreateView";
    private static final String ON_VIEW_CREATED_METHOD = "onViewCreated";
    private static final String ON_RESUME_METHOD = "onResume";
    private static final String ON_ATTACH_METHOD = "onAttach";
    private static final String INIT_VIEWS_METHOD = "initViews";
    private static final String INIT_LISTENERS_METHOD = "initListeners";

    public static TypeSpec generateClass(FragmentAnnotatedClass annotatedClass) {
        List<VariableElement> views = annotatedClass.getViews();
        TypeSpec.Builder builder = classBuilder("Generated" + annotatedClass.getName() + "Fragment")
                .addModifiers(PUBLIC, FINAL)
                .superclass(FRAGMENT_CLASS)
                .addMethod(buildOnCreateMethod(annotatedClass))
                .addMethod(buildOnCreateViewMethod(annotatedClass.getLayoutId()))
                .addMethod(buildOnViewCreatedMethod())
                .addMethod(buildOnResumeMethod(annotatedClass))
                .addMethod(buildInitViewsMethod(annotatedClass))
                .addMethod(buildInitListenersMethod(annotatedClass))
                .addMethod(buildOnAttachMethod(annotatedClass))
                .addField(ClassName.get(annotatedClass.getType()), SCREEN_VARIABLE, PRIVATE);
        if (annotatedClass.getScreenListener() != null) {
            builder.addField(ClassName.get(annotatedClass.getScreenListener().asType()), SCREEN_LISTENER_VARIABLE, PRIVATE);
        } else {
            builder.addField(ClassName.get(Object.class), SCREEN_LISTENER_VARIABLE, PRIVATE);
        }
        for (VariableElement view : views) {
            builder.addField(ClassName.get(view.asType()), view.getSimpleName().toString(), PRIVATE);
        }
        return builder.build();
    }

    private static MethodSpec buildOnAttachMethod(FragmentAnnotatedClass annotatedClass) {
        MethodSpec.Builder onAttachSpec = methodBuilder(ON_ATTACH_METHOD)
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ACTIVITY_CLASS, "activity")
                .addStatement("super.onAttach(activity);");
        if(annotatedClass.getScreenListener() != null) {
            final Name screenListenerName = annotatedClass.getScreenListener().getSimpleName();
            onAttachSpec
                    .beginControlFlow("if (activity instanceof $L.$L) ", annotatedClass.getName(), screenListenerName)
                    .addStatement("$L = ($L.$L) activity", SCREEN_LISTENER_VARIABLE, annotatedClass.getName(), screenListenerName)
                    .endControlFlow()
                    .beginControlFlow("else")
                    .addStatement("throw new UnsupportedOperationException(String.format(\"%s must implement $L.$L\", activity.getClass().getSimpleName()))",
                            annotatedClass.getName(), screenListenerName)
                    .endControlFlow();
        }
        return onAttachSpec.build();
    }

    private static MethodSpec buildOnResumeMethod(FragmentAnnotatedClass annotatedClass) {
        MethodSpec.Builder onCreateSpec = methodBuilder(ON_RESUME_METHOD)
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("super.onResume()");
        if (!annotatedClass.getLifeCycleMethods().isEmpty()) {
            ExecutableElement onCreateMethod = getLifeCycleMethodByState(annotatedClass, State.ON_RESUME);
            if (onCreateMethod != null) {
                onCreateSpec.addStatement("$L.$L()", SCREEN_VARIABLE, onCreateMethod.getSimpleName());
            }
        }
        return onCreateSpec.build();
    }

    private static MethodSpec buildOnCreateMethod(FragmentAnnotatedClass annotatedClass) {
        MethodSpec.Builder onCreateSpec = methodBuilder(ON_CREATE_METHOD)
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(BUNDLE_CLASS, "savedInstanceState")
                .addStatement("super.onCreate(savedInstanceState)")
                .addStatement("$L = new $L()", SCREEN_VARIABLE, annotatedClass.getName());
        if (!annotatedClass.getLifeCycleMethods().isEmpty()) {
            ExecutableElement onCreateMethod = getLifeCycleMethodByState(annotatedClass, State.ON_CREATE);
            if (onCreateMethod != null) {
                onCreateSpec.addStatement("$L.$L()", SCREEN_VARIABLE, onCreateMethod.getSimpleName());
            }
        }
        return onCreateSpec.build();
    }

    private static ExecutableElement getLifeCycleMethodByState(FragmentAnnotatedClass annotatedClass, State state) {
        for (ExecutableElement executableElement : annotatedClass.getLifeCycleMethods()) {
            State value = executableElement.getAnnotation(LifeCycle.class).value();
            if (value.equals(state)) {
                return executableElement;
            }
        }
        return null;
    }

    private static MethodSpec buildOnCreateViewMethod(int layoutId) {
        return methodBuilder(ON_CREATE_VIEW_METHOD)
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(LAYOUT_INFLATER_CLASS, "inflater")
                .addParameter(VIEW_GROUP_CLASS, "container")
                .addParameter(BUNDLE_CLASS, "savedInstanceState")
                .returns(VIEW_CLASS)
                .addStatement("super.onCreateView(inflater, container, savedInstanceState)")
                .addStatement("return inflater.inflate($L, container, false)", layoutId)
                .build();
    }

    private static MethodSpec buildOnViewCreatedMethod() {
        return methodBuilder(ON_VIEW_CREATED_METHOD)
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(VIEW_CLASS, "view")
                .addParameter(BUNDLE_CLASS, "savedInstanceState")
                .addStatement("super.onViewCreated(view, savedInstanceState)")
                .addStatement("$L()", INIT_VIEWS_METHOD)
                .addStatement("$L()", INIT_LISTENERS_METHOD)
                .build();
    }

    private static MethodSpec buildInitViewsMethod(FragmentAnnotatedClass annotatedClass) {
        final MethodSpec.Builder initViewsMethodBuilder = methodBuilder(INIT_VIEWS_METHOD)
                .addModifiers(PRIVATE);
        for (VariableElement view : annotatedClass.getViews()) {
            String viewName = view.getSimpleName().toString();
            initViewsMethodBuilder
                    .addStatement("$L = ($L) getActivity().findViewById($L)",
                            viewName, view.asType(), view.getAnnotation(Bind.class).value())
                    .addStatement("$L.$L = this.$L", SCREEN_VARIABLE, viewName, viewName);
        }
        return initViewsMethodBuilder
                .addStatement("$L.init(getContext(), $L)", SCREEN_VARIABLE, SCREEN_LISTENER_VARIABLE)
                .build();
    }

    private static MethodSpec buildInitListenersMethod(FragmentAnnotatedClass annotatedClass) {
        final MethodSpec.Builder builder = methodBuilder(INIT_LISTENERS_METHOD)
                .addModifiers(PRIVATE);
        for (ExecutableElement method: annotatedClass.getClickMethods()) {
            final int value = method.getAnnotation(Click.class).value();
            builder.beginControlFlow("getActivity().findViewById($L).setOnClickListener(new View.OnClickListener()", value)
                    .addCode("@Override\n")
                    .beginControlFlow("public void onClick(View v)")
                    .addStatement("$L.$L()", SCREEN_VARIABLE, method.getSimpleName())
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement(")");
        }
        return builder.build();
    }
}
