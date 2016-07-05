package bg.mentormate.apdemo.utils;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

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
}
