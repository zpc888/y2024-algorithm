package run;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface MetricsRuntime {
    int seconds() default 0;
    int ms();
    double beats() default -1;
    String description() default "";
}
