package bg.mentormate.apdemo;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
public final class Utils {

    public static String getPackageName(Elements elementUtils, TypeElement type)
            throws NoPackageNameException {
        PackageElement pkg = elementUtils.getPackageOf(type);
        if (pkg.isUnnamed()) {
            throw new NoPackageNameException(type);
        }
        return pkg.getQualifiedName().toString();
    }
}
