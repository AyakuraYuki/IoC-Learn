package cc.ayakurayuki.ioc.base.context;

import cc.ayakurayuki.ioc.base.entity.Bean;
import cc.ayakurayuki.ioc.base.entity.Property;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 为{@link ApplicationContext}提供XML支持的辅助类
 * <p>
 * cc.ayakurayuki.ioc.base.context
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:27
 */
public class ApplicationContextHandler extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(ApplicationContextHandler.class.getName());

    /**
     * {@code <bean></bean>}
     */
    private static final String ELEMENT_BEAN = "bean";

    /**
     * {@code <bean }<code><strong>id=""</strong></code>{@code ></bean>}
     */
    private static final String ATTRIBUTE_ID = "id";

    /**
     * {@code <bean }<code><strong>class=""</strong></code>{@code ></bean>}
     */
    private static final String ATTRIBUTE_CLASS = "class";

    /**
     * {@code <property></property>}
     */
    private static final String ELEMENT_PROPERTY = "property";

    /**
     * {@code <property }<code><strong>name=""</strong></code>{@code ></property>}
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * {@code <property }<code><strong>ref=""</strong></code>{@code ></property>}
     */
    private static final String ATTRIBUTE_REF = "ref";

    /**
     * Bean容器
     * <p>
     * 仅存放本次读取中获取的所有bean
     */
    private List<Bean> beans = new ArrayList<>();

    /**
     * 由于property需要使用到{@link Bean#addProperty(Property)}, 所以需要单独设立一个成员变量
     */
    private Bean bean;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (ELEMENT_BEAN.equals(qName)) {
            bean = new Bean();
            bean.setId(attributes.getValue(ATTRIBUTE_ID));
            bean.setClassPath(attributes.getValue(ATTRIBUTE_CLASS));
            beans.add(bean);
            logger.log(Level.INFO, "Bean named '" + bean.getId() + "' has been added into list.");
        } else if (ELEMENT_PROPERTY.equals(qName)) {
            Property property = new Property();
            property.setName(attributes.getValue(ATTRIBUTE_NAME));
            property.setRef(attributes.getValue(ATTRIBUTE_REF));
            bean.addProperty(property);
            logger.log(Level.INFO, "Property named '" + property.getName() + "' has been added into bean '" + bean.getId() + "'.");
        }
    }

    public List<Bean> getBeans() {
        return this.beans;
    }

}
