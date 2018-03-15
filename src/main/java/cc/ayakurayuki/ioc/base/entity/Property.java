package cc.ayakurayuki.ioc.base.entity;

/**
 * Bean配置的property标签支持
 * <p>
 * cc.ayakurayuki.ioc.base.entity
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:04
 */
public class Property {

    /**
     * 配置名称
     */
    private String name;

    /**
     * 需要注入的Bean的ID
     */
    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

}
