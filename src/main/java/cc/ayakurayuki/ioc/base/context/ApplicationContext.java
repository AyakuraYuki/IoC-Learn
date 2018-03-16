package cc.ayakurayuki.ioc.base.context;

import cc.ayakurayuki.ioc.base.entity.Bean;
import cc.ayakurayuki.ioc.base.entity.Property;
import cc.ayakurayuki.ioc.base.exception.NoSuchBeanDefinitionException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 仿org.springframework.context.ApplicationContext的ApplicationContext
 * <p>
 * 采用单例模式设计
 * <p>
 * cc.ayakurayuki.ioc.base.context
 *
 * @author ayakurayuki
 * @date 2018/3/15-10:03
 */
public class ApplicationContext {

    private static final Logger logger = Logger.getLogger(ApplicationContext.class.getName());

    /**
     * 支持的配置文件类型
     */
    private static final String SUFFIX = "xml";

    /**
     * 工厂容器, 装载所有Bean
     */
    private HashMap<String, Bean> beans = new HashMap<>();

    /**
     * 单例模式静态内部类设计方式
     */
    private static class ApplicationContextSingletonHolder {
        private static final ApplicationContext INSTANCE = new ApplicationContext();
    }

    /**
     * 单例模式私有构造器
     */
    private ApplicationContext() {
        this.initialize();
    }

    /**
     * 初始化方法
     * <p>
     * 初始化顺序为: 读取XML配置 -> 初始化Bean -> 读取property注入到Bean中
     */
    private void initialize() {
        List<Bean> beanList = readXML();
        initBean(beanList);
        setBeanProperties();
    }

    /**
     * 读取配置文件
     *
     * @return 全体bean列表
     */
    private List<Bean> readXML() {
        List<Bean> list = new ArrayList<>();
        File classpathFolder = new File(this.getClass().getResource("/").getFile());
        File[] files = classpathFolder.listFiles(((dir, name) -> name.endsWith(SUFFIX)));
        for (File xmlFile : Objects.requireNonNull(files)) {
            try {
                InputStream inputStream = new FileInputStream(xmlFile);
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                ApplicationContextHandler handler = new ApplicationContextHandler();
                parser.parse(inputStream, handler);
                list.addAll(handler.getBeans());
            } catch (SAXException | ParserConfigurationException | IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Initial beans from configured bean list.
     *
     * @param beanList From the return of {@link #readXML()}
     */
    private void initBean(List<Bean> beanList) {
        for (Bean bean : beanList) {
            try {
                Object object = Class.forName(bean.getClassPath()).newInstance();
                bean.setObject(object);
                beans.put(bean.getId(), bean);
                logger.log(Level.INFO, "Bean named '" + bean.getId() + "' has been initialized.");
            } catch (InstantiationException e) {
                System.err.println("Bean cannot be instantiated: " + e.getLocalizedMessage());
            } catch (IllegalAccessException e) {
                System.err.println(e.getLocalizedMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Bean not found: " + e.getLocalizedMessage());
            }
        }
    }

    /**
     * 通过setter注入设置Bean中需要的对象
     */
    private void setBeanProperties() {
        // 遍历beans: HashMap<String, Bean>
        for (HashMap.Entry<String, Bean> entry : beans.entrySet()) {
            // 以Map.Entry的方式访问beans的对象
            Bean bean = entry.getValue();
            // 从Bean中获取注入的properties
            List<Property> properties = bean.getProperties();
            if (null != properties) {
                // 如果properties存在, 遍历properties
                for (Property property : properties) {
                    // 获取引用的bean的名称
                    String ref = property.getRef();
                    // 由于使用了reference, 所以必定会存在于beans容器中, 则进入beans容器搜索
                    if (beans.containsKey(ref)) {
                        // 从搜索到的bean获取对应的实例对象
                        Object refObject = beans.get(ref).getObject();
                        Object beanObject = bean.getObject();
                        try {
                            setterInjection(beanObject, property.getName(), refObject);
                        } catch (RuntimeException e) {
                            constructInjection(bean, new Class<?>[]{refObject.getClass()}, refObject);
                        }
                    } else {
                        // 当然，如果找不到ref使用到的bean, 抛出异常
                        throw new RuntimeException("Target reference bean named '" + ref + "' not found.");
                    }
                }
            }
        }
    }

    /**
     * Inject by setter style.
     */
    private static void setterInjection(Object targetObject, String attributeName, Object injectObject) {
        // In generally the prefix of every setter is "set".
        final String setterPrefix = "set";
        // Generate target method name.
        final String methodName = setterPrefix + toUpperCamelCase(attributeName);
        try {
            // Get target method.
            Method targetSetter = targetObject.getClass().getMethod(methodName, injectObject.getClass());
            // Inject reference object into target object's attribute.
            targetSetter.invoke(targetObject, injectObject);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Target method named '" + methodName + "' not found.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access to method '" + methodName + "', probably it is private.");
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Inject by construct style. (Recommended)
     */
    private static void constructInjection(Bean bean, Class<?>[] parameterTypes, Object... injectObjects) {
        Class<?> clazz = bean.getObject().getClass();
        try {
            Constructor constructor = clazz.getConstructor(parameterTypes);
            bean.setObject(constructor.newInstance(injectObjects));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Target constructor not found.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access to constructor, probably it is private.");
        } catch (InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将属性名转换为类名（大驼峰命名）
     *
     * @param name 属性名
     * @return 类名
     */
    private static String toUpperCamelCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 获取ApplicationContext单例
     *
     * @return {@link ApplicationContextSingletonHolder#INSTANCE}
     */
    public static ApplicationContext getInstance() {
        return ApplicationContextSingletonHolder.INSTANCE;
    }

    /**
     * 从工厂容器{@link #beans}中获取{@link Bean}
     *
     * @param beanID {@link Bean#id} Bean名称
     * @return {@link Bean#object} Bean对应的对象
     */
    public Object getBean(String beanID) {
        Bean bean = beans.get(beanID);
        if (null == bean) {
            throw new NoSuchBeanDefinitionException(beanID);
        }
        return bean.getObject();
    }

}
