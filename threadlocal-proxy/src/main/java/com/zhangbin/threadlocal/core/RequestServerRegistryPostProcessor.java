package com.zhangbin.threadlocal.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import static org.springframework.util.Assert.notEmpty;

/**
 * @author zhangbin
 * @Type RequestServerRegistryPostProcessor
 * @Desc 非spring boot 项目使用
 * @date 2019-03-17
 * @Version V1.0
 */
public class RequestServerRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, InitializingBean {


    private String[] basePackages;

    public RequestServerRegistryPostProcessor(String[] basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        RequestServerClassPathBeanDefinitionScanner scanner = new RequestServerClassPathBeanDefinitionScanner(registry);
        scanner.scan(this.basePackages);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }


    @Override
    public void afterPropertiesSet() {
        notEmpty(this.basePackages, "Property 'basePackages' is required");
    }

}
