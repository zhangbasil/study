package com.zhangbin.threadlocal.core.autoconfigure;

import com.zhangbin.threadlocal.annotation.RequestServer;
import com.zhangbin.threadlocal.core.RequestServerScannerRegistrar;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangbin
 * @Type RequestServerAutoConfiguration
 * @Desc
 * @date 2019-03-24
 * @Version V1.0
 */
@Configuration
@ConditionalOnClass(RequestServer.class)
@Import(RequestServerScannerRegistrar.class)
public class RequestServerAutoConfiguration {
}
