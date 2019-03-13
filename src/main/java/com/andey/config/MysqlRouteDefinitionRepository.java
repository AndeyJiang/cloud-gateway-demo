//package com.andey.config;
//
//import com.alibaba.fastjson.JSONObject;
//import com.andey.dao.TGateWayRouteDefinitionMapper;
//import com.andey.entity.TGateWayRouteDefinition;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.FilterDefinition;
//import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by jiangbin on 2018/10/10.
// *
// * 当手动更新数据库后，需要发布ruote信息刷新事件
// * 刷新事件地址：
// * http://localhost:8080/actuator/gateway/refresh
// */
//@Component
//public class MysqlRouteDefinitionRepository implements RouteDefinitionRepository {
//
//    @Autowired
//    TGateWayRouteDefinitionMapper tGateWayRouteDefinitionMapper;
//
//    @Override
//    public Flux<RouteDefinition> getRouteDefinitions() {
//
//        List<RouteDefinition> routeDefinitions = new ArrayList<>();
//
//       // List<TGateWayRouteDefinition> gatewayList = tGateWayRouteDefinitionMapper.selectList(new QueryWrapper<>());
//        List<TGateWayRouteDefinition> gatewayList = tGateWayRouteDefinitionMapper.findByName();
//        /**
//         * 这里不用再判断了，必须有的
//         */
//        for (TGateWayRouteDefinition gatewayList1 : gatewayList) {
//            RouteDefinition definition = new RouteDefinition();
//            /**
//             * 公共部分信息，（兼容）集成eureka，客户端的负载均衡。域名直连或以注册发的现服务名调用
//             */
//            definition.setId(gatewayList1.getId());
//            //definition.setUri(UriComponentsBuilder.fromHttpUrl(gatewayList1.getUri()).build().toUri());
//            definition.setUri(URI.create(gatewayList1.getUri()));
//            /**
//             * predicates信息
//             */
//            List<PredicateDefinition> predicates = JSONObject.parseArray(gatewayList1.getPredicates(), PredicateDefinition.class);
//            definition.setPredicates(predicates);
//            /**
//             * filters信息
//             */
//            if(gatewayList1.getFilters()!=null){
//                List<FilterDefinition> filters=JSONObject.parseArray(gatewayList1.getFilters(),FilterDefinition.class);
//
//                definition.setFilters(filters);
//            }
//
//            /**
//             * 将definition放到集合中
//             */
//            routeDefinitions.add(definition);
//        }
//        /**
//         * 返回封装后的路由信息给应用程序
//         */
//        System.out.print(JSONObject.toJSONString(routeDefinitions));
//        return Flux.fromIterable(routeDefinitions);
//    }
//
//
//    @Override
//    public Mono<Void> save(Mono<RouteDefinition> route) {
//
//        return null;
//    }
//
//    @Override
//    public Mono<Void> delete(Mono<String> routeId) {
//        return null;
//    }
//
//
//}
