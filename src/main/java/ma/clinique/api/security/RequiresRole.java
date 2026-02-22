package ma.clinique.api.security;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import ma.clinique.model.enums.Role;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface RequiresRole {
  Role[] value();
}
