package com.andey.config.redisConfig;

import com.alibaba.fastjson.JSONObject;
import com.andey.dao.TGateWayRouteDefinitionMapper;
import com.andey.entity.TGateWayRouteDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * Created by jiangbin on 2019/3/12.
 */
@Slf4j
@Service
public class GatewayService implements ApplicationEventPublisherAware, CommandLineRunner {
    public static final String  GATEWAY_ROUTES = "gateway_routes";

    @Autowired
    private RedisRouteDefinitionRepository routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    TGateWayRouteDefinitionMapper tGateWayRouteDefinitionMapper;


    public String save() {

        //重数据库获取所有route信息
        List<TGateWayRouteDefinition> gatewayList = tGateWayRouteDefinitionMapper.findByName();
        log.info("网关配置信息：=====>"+ JSONObject.toJSONString(gatewayList));
        //先清空redis网关路由
        Boolean result=redisTemplate.delete(GATEWAY_ROUTES);
        log.info("为保持与数据库数据同步：项目启动及动态route信息刷新需要先清空redis网关路由->{}",result);
        gatewayList.forEach(gatewayRoute -> {
            RouteDefinition definition = new RouteDefinition();
            /**
             * 公共部分信息，（兼容）集成eureka，客户端的负载均衡。域名直连或以注册发的现服务名调用
             */
            definition.setId(gatewayRoute.getId());
            definition.setUri(URI.create(gatewayRoute.getUri()));
            /**
             * predicates信息
             */
            List<PredicateDefinition> predicates = JSONObject.parseArray(gatewayRoute.getPredicates(), PredicateDefinition.class);
            definition.setPredicates(predicates);
            /**
             * filters信息
             */
            if(gatewayRoute.getFilters()!=null){
                List<FilterDefinition> filters=JSONObject.parseArray(gatewayRoute.getFilters(),FilterDefinition.class);

                definition.setFilters(filters);
            }
            //在写入redis路由
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        });
        return "success";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.publisher = applicationEventPublisher;
    }
    @Override
    public void run(String... args){
        this.save();
    }

    public void deleteRoute(String routeId){
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
    }
}
