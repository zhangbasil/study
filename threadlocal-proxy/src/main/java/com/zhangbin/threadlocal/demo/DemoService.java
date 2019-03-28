package com.zhangbin.threadlocal.demo;

import com.zhangbin.threadlocal.annotation.RequestLine;
import com.zhangbin.threadlocal.annotation.RequestServer;

/**
 * @author zhangbin
 * @Type DemoService
 * @Desc
 * @date 2019-03-04
 * @Version V1.0
 */
@RequestServer(url = "${demo.server}")
public interface DemoService {

    @RequestLine(path = "/index", method = RequestLine.RequestMethod.GET)
    String sendReq(String name);


}
