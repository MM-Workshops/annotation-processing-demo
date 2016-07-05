package bg.mentormate.apdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by TL on 7/5/2016.
 */
@Retention(CLASS)
@Target(ElementType.METHOD)
public @interface LifeCycle {
    State value();
}
