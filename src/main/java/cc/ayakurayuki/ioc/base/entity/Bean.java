package cc.ayakurayuki.ioc.base.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本Bean封装类
 * <p>
 * 按照Spring风格ApplicationContext.xml设计
 * <p>
 * 则该类将拥有如下成员变量
 * <pre>
 * id: {@link String}
 * classPath: {@link String}
 * properties: {@link cc.ayakurayuki.ioc.base.entity.Property}
 * object: {@link Object}
 * </pre>
 * <p>
 * cc.ayakurayuki.ioc.base.entity
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:03
 */
public class Bean {

    /**
     * Bean ID标识
     */
    private String id;

    /**
     * 被注入对象的包路径
     */
    private String classPath;

    /**
     * 额外配置属性
     */
    private List<Property> properties;

    /**
     * 注入对象实例
     */
    private Object object;

    /* ==================== getter / setter ==================== */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    /* ==================== property添加方法 ==================== */

    /**
     * 用于添加property配置项
     *
     * @param property 通过property标签获得的配置项
     */
    public void addProperty(Property property) {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        properties.add(property);
    }

}
