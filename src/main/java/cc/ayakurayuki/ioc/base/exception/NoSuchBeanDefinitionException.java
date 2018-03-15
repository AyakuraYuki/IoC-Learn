package cc.ayakurayuki.ioc.base.exception;

import cc.ayakurayuki.ioc.base.entity.Bean;

/**
 * 找不到Bean时抛出的异常
 * <p>
 * cc.ayakurayuki.ioc.base.exception
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:45
 */
public class NoSuchBeanDefinitionException extends RuntimeException {

    private String beanName;

    /**
     * 创建一个新的 {@code NoSuchBeanDefinitionException}.
     *
     * @param name 丢失的Bean的名称{@link Bean#id}
     */
    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
        this.beanName = name;
    }

    /**
     * 创建一个新的 {@code NoSuchBeanDefinitionException}.
     *
     * @param name    丢失的Bean的名称{@link Bean#id}
     * @param message 问题的详细描述信息
     */
    public NoSuchBeanDefinitionException(String name, String message) {
        super("No bean named '" + name + "' available: " + message);
        this.beanName = name;
    }

    /**
     * 返回丢失的Bean的名称
     */
    public String getBeanName() {
        return this.beanName;
    }

}
