package bg.mentormate.apdemo;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public final class FragmentScreenValidator {
    static boolean isPublic(TypeElement annotatedClass) {
        return annotatedClass.getModifiers().contains(PUBLIC);
    }

    static boolean isAbstract(TypeElement annotatedClass) {
        return annotatedClass.getModifiers().contains(ABSTRACT);
    }

    public static boolean implementFragmentScreen(TypeElement annotatedClass) {
        final List<? extends TypeMirror> interfaces = annotatedClass.getInterfaces();
        for (TypeMirror anInterface : interfaces) {
            if (anInterface.toString().contains("FragmentScreen")) {
                return true;
            }
        }
        return false;
    }
}
