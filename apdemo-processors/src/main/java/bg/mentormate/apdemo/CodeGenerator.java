package bg.mentormate.apdemo;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public class CodeGenerator {
    public static TypeSpec generateClass(AnnotatedClass annotatedClass) {
        TypeSpec.Builder builder =  classBuilder("Generated" + annotatedClass.annotatedClassName)
                .addModifiers(PUBLIC, FINAL);
        builder.addMethod(makeHelloMethod(annotatedClass));
        return builder.build();
    }

    private static MethodSpec makeHelloMethod(AnnotatedClass annotatedClass) {
        return methodBuilder("sayHello")
                .addModifiers(PUBLIC, STATIC)
                .addStatement(String.format("return \"Hello %s\"", annotatedClass.annotatedClassName))
                .returns(String.class)
                .build();
    }
}
