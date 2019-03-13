package com.andey.cotroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangbin on 2019/1/29.
 */
@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public String defaultfallback(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>();
        map.put("resultCode","fail");
        map.put("resultMessage","服务访问超时");
        map.put("resultObj","null");
        return map.toString();
    }
}
