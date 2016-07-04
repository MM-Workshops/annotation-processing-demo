package bg.mentormate.apdemo;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.JavaFile.builder;
import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class FragmentProcessor extends AbstractProcessor {

    private static final String ANNOTATION = "@" + Fragment.class.getSimpleName();

    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(
                Fragment.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            return doProcess(annotations, roundEnv);
        } catch (Throwable e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            messager.printMessage(Diagnostic.Kind.ERROR, writer.toString());
            return false;
        }
    }

    private boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ArrayList<AnnotatedClass> annotatedClasses = new ArrayList<>();
        // Gathering annotated classes
        for (Element annotated: roundEnv.getElementsAnnotatedWith(Fragment.class) ) {
            TypeElement annotatedClass = (TypeElement) annotated;
            if (!isValidClass(annotatedClass)) {
                messager.printMessage(Diagnostic.Kind.NOTE, annotatedClass.getSimpleName() + " class is not valid");
                return true;
            }
            try {
                annotatedClasses.add(buildAnnotatedClass(annotatedClass));
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        // Generating files
        try {
            generate(annotatedClasses);
        } catch (NoPackageNameException | IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        return false;
    }

    private boolean isValidClass(TypeElement annotatedClass) {
        if (!ClassValidator.isPublic(annotatedClass)) {
            String message = String.format("Classes annotated with %s must be public.",
                    ANNOTATION);
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }
        if (ClassValidator.isAbstract(annotatedClass)) {
            String message = String.format("Classes annotated with %s must not be abstract.",
                    ANNOTATION);
            messager.printMessage(ERROR, message, annotatedClass);
            return false;
        }
        return true;
    }

    private AnnotatedClass buildAnnotatedClass(TypeElement annotatedClass) {
        return new AnnotatedClass(annotatedClass);
    }

    private void generate(ArrayList<AnnotatedClass> annotatedClasses) throws NoPackageNameException, IOException {
        if (annotatedClasses.size() == 0) {
            return;
        }
        String packageName = Utils.getPackageName(processingEnv.getElementUtils(),
                annotatedClasses.get(0).typeElement);
        for (AnnotatedClass annotatedClass : annotatedClasses) {
            TypeSpec generatedClass = CodeGenerator.generateClass(annotatedClass);
            JavaFile javaFile = builder(packageName, generatedClass).build();
            javaFile.writeTo(processingEnv.getFiler());
        }
    }
}
