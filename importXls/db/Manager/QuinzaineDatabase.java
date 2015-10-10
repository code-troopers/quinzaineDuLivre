package net.vibrac.quinzaine.db.Manager;

/**
 * Created by Geoffroy Vibrac on 01/10/2015.
 */

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({FIELD, TYPE, METHOD})
public @interface QuinzaineDatabase {
}
