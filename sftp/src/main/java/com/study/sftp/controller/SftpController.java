package com.study.sftp.controller;

import com.study.sftp.core.SftpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
@RestController
@RequestMapping("/sftp")
public class SftpController {

    @Autowired
    private SftpTemplate sftpTemplate;


    @GetMapping("/download")
    public String download(@RequestParam(value = "threadNum", required = false) Integer threadNum) {
        sftpTemplate.download("", "");
        return "ok";
    }


}
