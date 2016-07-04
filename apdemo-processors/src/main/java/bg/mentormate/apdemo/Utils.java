package bg.mentormate.apdemo;

import java.lang.annotation.Annotation;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
final class Utils {

    static String getPackageName(Elements elementUtils, TypeElement type) throws NoPackageNameException {
        PackageElement packageElement = elementUtils.getPackageOf(type);
        if (packageElement.isUnnamed()) {
            throw new NoPackageNameException(type);
        }
        return packageElement.getQualifiedName().toString();
    }

    static String getClassAnnotationValue(Class classType, Class annotationType, String attributeName) {
        String value = null;

        Annotation annotation = classType.getAnnotation(annotationType);
        if (annotation != null) {
            try {
                value = (String) annotation.annotationType().getMethod(attributeName).invoke(annotation);
            } catch (Exception ex) {
            }
        }

        return value;
    }
}
