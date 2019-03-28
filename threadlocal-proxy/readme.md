
## ThreadLocal
* ThreadLocalMap

    * Entry
    
        * WeakReference
        

* 在Spring中使用 `@Autowired` HttpServletRequest 轻松就可以得到 request

* 原因：肯定是Spring容器在初始化的时候将 `HttpServletRequest` 注入到容器中

* Spring 能实现在多线程环境下，将各个线程的request进行隔离，且准确无误的进行注入，
奥秘 `ThreadLocal`

* `RequestContextHolder`


## 实战

* dubbo中 `com.alibaba.dubbo.rpc.RpcContext`

* `com.zhangbin.threadlocal.model.ReqContext` 通过拦截器做统一校验的时候
可将登录的用户的信息放到ReqContext中


## 动态代理 & spring 依赖注入 FeignClient Mybatis 等案例

### 框架实战

* Mybatis 中通过 `@MapperScan` 扫描包 就可以把对应的 Mapper 注入到Spring 容器中

* Dubbo 中通过 `@Service` 也可以把对应的实例注入到Spring 容器中

### 原理剖析

以Mybatis 为例

`org.mybatis.spring.mapper.ClassPathMapperScanner extends ClassPathBeanDefinitionScanner ` 


在默认构造方法 `super(registry, false);` false 为不使用默认的 Filter 将需要的
加入 `addIncludeFilter` 中

重写 `isCandidateComponent` 方法 不使用默认的

`@Override
   protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
     return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
   }
`


重写 doScan 处理对应的 BeanDefinitions 并注入到Spring 容器中

`
@Override
   public Set<BeanDefinitionHolder> doScan(String... basePackages) {
     Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
     if (beanDefinitions.isEmpty()) {
       logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
     } else {
       processBeanDefinitions(beanDefinitions);
     }
     return beanDefinitions;
   }
   `


查看 FeignClint & Dubbo & Mybatis 和Spring 整合基本大同小异




