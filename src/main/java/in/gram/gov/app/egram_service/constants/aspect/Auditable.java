package in.gram.gov.app.egram_service.constants.aspect;

import in.gram.gov.app.egram_service.constants.enums.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    ActionType actionType();
    String entityType();
    String description() default "";
}

