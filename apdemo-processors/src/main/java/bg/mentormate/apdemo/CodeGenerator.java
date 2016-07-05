package bg.mentormate.apdemo;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class CodeGenerator {
    private static final ClassName FRAGMENT = ClassName.get("android.app", "Fragment");
    private static final ClassName LAYOUT_INFLATER = ClassName.get("android.view", "LayoutInflater");
    private static final ClassName VIEW_GROUP = ClassName.get("android.view", "ViewGroup");
    private static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");
    private static final ClassName VIEW = ClassName.get("android.view", "View");
    private static final ClassName LOG = ClassName.get("android.util", "Log");

    public static TypeSpec generateClass(AnnotatedClass annotatedClass) {
        List<VariableElement> views = annotatedClass.getViews();
        TypeSpec.Builder builder =  classBuilder("Generated" + annotatedClass.getName())
                .addModifiers(PUBLIC, FINAL)
                .superclass(FRAGMENT)
                .addMethod(buildOnCreateViewMethod())
                .addMethod(buildInitViewsMethod(views));
        for (VariableElement view : views) {
            builder.addField(ClassName.get(view.asType()), view.getSimpleName().toString(), PRIVATE);
        }
        return builder.build();
    }

    private static MethodSpec buildInitViewsMethod(List<VariableElement> views) {
        final MethodSpec.Builder initViewsMethodBuilder = methodBuilder("initViews")
                .addModifiers(PRIVATE);
        for (VariableElement view : views) {
            String viewName = view.getSimpleName().toString();
            initViewsMethodBuilder.addStatement(
                    viewName + " = (" + view.asType() +") getActivity().findViewById(R.id."+ getViewIdByName(viewName) +")");
        }
        return initViewsMethodBuilder.build();
    }

    private static String getViewIdByName(String viewName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, viewName);
    }

    private static MethodSpec buildOnCreateViewMethod() {
        final MethodSpec.Builder onCreateViewBuilder = methodBuilder("onCreateView")
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(LAYOUT_INFLATER, "inflater")
                .addParameter(VIEW_GROUP, "container")
                .addParameter(BUNDLE, "savedInstanceState")
                .returns(VIEW)
                .addStatement("super.onCreateView(inflater, container, savedInstanceState)")
                .addStatement("initViews()")
                .addStatement("return inflater.inflate(R.layout.fragment_fragment1, container, false)");
        return onCreateViewBuilder.build();
    }
}
