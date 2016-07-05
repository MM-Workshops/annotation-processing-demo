package bg.mentormate.apdemo;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
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
    private static final ClassName FRAGMENT_CLASS = ClassName.get("android.support.v4.app", "Fragment");
    private static final ClassName LAYOUT_INFLATER_CLASS = ClassName.get("android.view", "LayoutInflater");
    private static final ClassName VIEW_GROUP_CLASS = ClassName.get("android.view", "ViewGroup");
    private static final ClassName BUNDLE_CLASS = ClassName.get("android.os", "Bundle");
    private static final ClassName VIEW_CLASS = ClassName.get("android.view", "View");
    private static final ClassName LOG_CLASS = ClassName.get("android.util", "Log");

    private static final String BUILDER_VARIABLE = "builder";

    private static final String ON_CREATE_VIEW_METHOD = "onCreateView";
    private static final String ON_VIEW_CREATED_METHOD = "onViewCreated";
    private static final String INIT_VIEWS_METHOD = "initViews";
    private static final String INIT_LISTENERS_METHOD = "initListeners";

    public static TypeSpec generateClass(FragmentAnnotatedClass annotatedClass) {
        List<VariableElement> views = annotatedClass.getViews();
        TypeSpec.Builder builder = classBuilder("Generated" + annotatedClass.getName() + "Fragment")
                .addModifiers(PUBLIC, FINAL)
                .superclass(FRAGMENT_CLASS)
                .addMethod(buildOnCreateViewMethod(annotatedClass.getLayoutId()))
                .addMethod(buildOnViewCreatedMethod())
                .addMethod(buildInitViewsMethod(annotatedClass))
                .addMethod(buildInitListenersMethod(annotatedClass))
                .addField(ClassName.get(annotatedClass.getType()), BUILDER_VARIABLE, PRIVATE);
        for (VariableElement view : views) {
            builder.addField(ClassName.get(view.asType()), view.getSimpleName().toString(), PRIVATE);
        }
        return builder.build();
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
                .addModifiers(PRIVATE)
                .addStatement("$L = new $L()", BUILDER_VARIABLE, annotatedClass.getName());
        for (VariableElement view : annotatedClass.getViews()) {
            String viewName = view.getSimpleName().toString();
            initViewsMethodBuilder
                    .addStatement("$L = ($L) getActivity().findViewById($T.id.$L)",
                            viewName, view.asType(), ClassName.get(bg.mentormate.apdemo.utils.Utils.getMainPackageName(annotatedClass), "R"), bg.mentormate.apdemo.utils.Utils.getViewIdByName(viewName))
                    .addStatement("$L.$L = this.$L", BUILDER_VARIABLE, viewName, viewName);
        }
        return initViewsMethodBuilder
                .addStatement("$L.init(getContext())", BUILDER_VARIABLE)
                .build();
    }

    private static MethodSpec buildInitListenersMethod(FragmentAnnotatedClass annotatedClass) {
        final MethodSpec.Builder builder = methodBuilder(INIT_LISTENERS_METHOD)
                .addModifiers(PRIVATE);
        for (ExecutableElement method: annotatedClass.getMethods()) {
            final String methodSimpleName = method.getSimpleName().toString();
            final String viewField = methodSimpleName.substring(0, methodSimpleName.indexOf("Clicked"));
            builder.beginControlFlow("$L.setOnClickListener(new View.OnClickListener()", viewField)
                    .addCode("@Override\n")
                    .beginControlFlow("public void onClick(View v)")
                    .addStatement("$L.$L()", BUILDER_VARIABLE, methodSimpleName)
                    .endControlFlow()
                    .endControlFlow()
                    .addStatement(")");
        }
        return builder.build();
    }
}
