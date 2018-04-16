package com.zccp.mini.pro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenmeng
 * @date 2018-04-16 9:28
 * @desc
 */

@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("/index")
    public String demo(){
        System.out.printf("success");
        LOG.info("test");
        return "SUCCESS";
    }
}
