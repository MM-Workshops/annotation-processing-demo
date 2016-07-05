package bg.mentormate.apdemo.utils;

import com.google.common.base.CaseFormat;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import bg.mentormate.apdemo.*;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public final class Utils {

    public static String getPackageName(Elements elementUtils, TypeElement type) throws NoPackageNameException {
        PackageElement packageElement = elementUtils.getPackageOf(type);
        if (packageElement.isUnnamed()) {
            throw new NoPackageNameException(type);
        }
        return packageElement.getQualifiedName().toString();
    }

    public static String getMainPackageName(FragmentAnnotatedClass annotatedClass) {
        final String[] split = annotatedClass.getType().toString().split("\\.");
        String packageName = "";
        if (split.length >= 3) {
            for (int i = 0; i < 3; i++) {
                packageName += split[i];
                if (i < 2) {
                    packageName += ".";
                }
            }
        }
        return packageName;
    }

    public static String getViewIdByName(String viewName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, viewName);
    }
}
