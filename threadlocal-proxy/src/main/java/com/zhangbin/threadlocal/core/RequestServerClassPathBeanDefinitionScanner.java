package com.zhangbin.threadlocal.core;

import com.zhangbin.threadlocal.annotation.RequestServer;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Map;
import java.util.Set;

/**
 * @author zhangbin
 * @Type RequestServerClassPathBeanDefinitionScanner
 * @Desc
 * @date 2019-03-17
 * @Version V1.0
 */
public class RequestServerClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {


    private static final String REQUEST_SERVER = "com.zhangbin.threadlocal.annotation.RequestServer";




    public RequestServerClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        // 添加需要过滤的注解类型
        addIncludeFilter(new AnnotationTypeFilter(RequestServer.class));
    }


    /**
     *
     * 扩展扫描路径
     *
     * @param basePackages
     * @return
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 获取 符合 RequestServer 的BeanDefinitionHolder
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        // 处理符合条件的的BeanDefinitionHolder
        processBeanDefinitions(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            ScannedGenericBeanDefinition beanDefinition = (ScannedGenericBeanDefinition) holder.getBeanDefinition();
            // 获取RequestServer
            Map<String, Object> annotationAttributes = beanDefinition.getMetadata().getAnnotationAttributes(REQUEST_SERVER);
            assert annotationAttributes != null;
            Object url = annotationAttributes.get("url");
            String beanClassName = beanDefinition.getBeanClassName();
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, beanClassName);
            constructorArgumentValues.addIndexedArgumentValue(1, url);
            // 通过构造方法的形式构建 RequestServerFactoryBean 的代理工厂类 并将接口注入到spring 容器中
            beanDefinition.setBeanClass(RequestServerFactoryBean.class);
        }

    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

}
