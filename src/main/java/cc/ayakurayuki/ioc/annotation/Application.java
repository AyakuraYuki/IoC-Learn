package cc.ayakurayuki.ioc.annotation;

import java.lang.annotation.*;

/**
 * cc.ayakurayuki.ioc.annotation
 *
 * @author ayakurayuki
 * @date 2018/3/15-09:57
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Application {

    String value() default "";

}
