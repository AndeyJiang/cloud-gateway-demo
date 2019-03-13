package com.andey.cotroller;

import com.andey.config.redisConfig.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiangbin on 2019/3/12.
 */
@RestController
@RequestMapping(value="/gateway")
public class RefreshRouteController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    GatewayService gatewayService;

    @PostMapping(value = "/refresh/{key}")
    public  String refresh(@PathVariable String key){

        if(!passwordEncoder.matches("gateway", key)){
            return "抱歉，您没有刷新权限!";
        }
        return gatewayService.save();
    }

    @PostMapping(value = "/encode")
    public String encode() {
        String encodekey=passwordEncoder.encode("gateway");
        return  encodekey;
    }
}
