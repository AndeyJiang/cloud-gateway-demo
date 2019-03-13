package com.andey.cotroller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangbin on 2019/1/21.
 */
@RestController
public class TestController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @RequestMapping("/test")
    public  String test(){
        redisTemplate.opsForSet().add("test","123");

        return "hello";

    }

    @RequestMapping("/timeout")
    public String timeout(String s){
        try{
            //睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject obj=new JSONObject();
        obj.put("1","测试");
        obj.put("2","反测试");
        return JSONObject.toJSONString(obj);
    }

}
