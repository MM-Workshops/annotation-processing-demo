package bg.mentormate.apdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by tung.lam.nguyen on 06.07.2016
 */

@Retention(CLASS)
@Target(ElementType.TYPE)
public @interface Listener {
}