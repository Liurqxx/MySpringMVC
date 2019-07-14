package com.itxiaoqiang;

import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 从spring ioc容器中扫描mvc的bean并装载
 *
 * @author hello_liu
 */
public class MvcBeanFactory {


    private ApplicationContext applicationContext;

    //注册器
    private HashMap<String, MvcBean> apiMap = new HashMap<>();

    public MvcBeanFactory(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext, "argument 'applicationContext' must not be null");
        this.applicationContext = applicationContext;

        //初始加载所有的bean
        loadApiFromSpringBeans();
    }

    private void loadApiFromSpringBeans() {

        String[] names = applicationContext.getBeanDefinitionNames();

        Class<?> type;
        for (String name : names
        ) {
            type = applicationContext.getType(name);
            for (Method m : type.getDeclaredMethods()) {
                //通过反射拿到HttpMapping注解
                MvcMapping mvcMapping = m.getAnnotation(MvcMapping.class);
                if (mvcMapping != null) {
                    //将带有MvcMapping注解的类封装成一个MVCBean
                    addApiItem(mvcMapping, name, m);
                }


            }

        }

    }

    public MvcBean getMvcBean(String url) {
        return apiMap.get(url);
    }

    private void addApiItem(MvcMapping mvcMapping, String beanName, Method method) {
        //拿到对应的bean
        MvcBean apiRun = new MvcBean();

        //拿到对应的注解value
        apiRun.apiName = mvcMapping.value();
        //标注指定注解的方法
        apiRun.targetMethod = method;

        apiRun.targetName = beanName;
        apiRun.context = this.applicationContext;

        apiMap.put(mvcMapping.value(), apiRun);
    }

    public static class MvcBean {
        String apiName;
        String targetName;
        Object target;
        Method targetMethod;
        ApplicationContext context;

        public Object run(Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (target == null) {
                // spring ioc 容器里面去服务Bean 比如GoodsServiceImpl
                target = context.getBean(targetName);
            }
            return targetMethod.invoke(target, args);
        }

        public Method getTargetMethod() {
            return targetMethod;
        }
    }


}
