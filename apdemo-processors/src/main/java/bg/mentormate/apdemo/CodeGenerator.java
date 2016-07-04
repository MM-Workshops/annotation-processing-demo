package bg.mentormate.apdemo;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class CodeGenerator {
    public static TypeSpec generateClass(AnnotatedClass annotatedClass) {

        TypeSpec.Builder builder =  classBuilder("Generated" + annotatedClass.getName())
                .addModifiers(PUBLIC, FINAL);
        builder.superclass(ClassName.get("android.app", "Fragment"))
                .addMethod(buildOnCreateViewMethod(annotatedClass));
        return builder.build();
    }

    private static MethodSpec buildOnCreateViewMethod(AnnotatedClass annotatedClass) {
        final MethodSpec.Builder onCreateViewBuilder = methodBuilder("onCreateView")
                .addModifiers(PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("android.view", "LayoutInflater"), "inflater")
                .addParameter(ClassName.get("android.view", "ViewGroup"), "container")
                .addParameter(ClassName.get("android.os", "Bundle"), "savedInstanceState")
                .returns(ClassName.get("android.view", "View"))
                .addStatement("super.onCreateView(inflater, container, savedInstanceState)");
        StringBuilder builder = new StringBuilder();
        for (String s : annotatedClass.getViews()) {
            builder.append(s);
        }
        onCreateViewBuilder
                .addStatement("$T.d(\"Cool\", \""+builder.toString() +"\")", ClassName.get("android.util", "Log"))
                .addStatement("return inflater.inflate(R.layout.fragment_fragment1, container, false)");
        return onCreateViewBuilder.build();
    }
}
