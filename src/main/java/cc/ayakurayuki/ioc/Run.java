package cc.ayakurayuki.ioc;

import cc.ayakurayuki.ioc.annotation.Application;
import cc.ayakurayuki.ioc.base.context.ApplicationContext;
import cc.ayakurayuki.ioc.module.entity.Man;
import cc.ayakurayuki.ioc.module.service.ManService;

/**
 * cc.ayakurayuki.ioc
 *
 * @author ayakurayuki
 * @date 2018/3/15-09:56
 */
@Application
public class Run {

    public static void main(String[] args) {
        ApplicationContext context = ApplicationContext.getInstance();
        ManService manService = (ManService) context.getBean("manService");
        Man man = manService.get();
        System.out.println(man);
    }

}
