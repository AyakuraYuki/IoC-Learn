package cc.ayakurayuki.ioc.annotation;

import java.lang.annotation.*;

/**
 * cc.ayakurayuki.ioc.annotation
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:01
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

}
