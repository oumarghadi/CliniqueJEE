package ma.clinique.api.security;

import java.lang.annotation.*;
import ma.clinique.model.enums.Role;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequiresRole {
  Role[] value();
}