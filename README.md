# cloud-gateway-demo
Redis+Mysql存储路由信息
# spring-cloud-gateway
基于版本springboot2  cloud:Finchley.RELEASE  微服务网关

1、基于数据库存储路由信息并在初始化或动态刷新时将route信息写入redis，每一次客户请求将从redis匹配数据，这样既
解决的数据库压力同时提供效率。要点在与对RouteDefinition数据结构进行适合自己的表结构设计。
基于数据库+redis，其实目的就是为了动态更新数据，动态刷新，做到时时同步。如果你想网关作为一个后台管理系统，那基于数据库是最好的选择。
gateway装配性能很强大，本demo基本的路由、限流、断路器功能都有实现。

2、表结构设计  
DROP TABLE IF EXISTS `t_gateway_route`;
CREATE TABLE `t_gateway_route` (
  `ID` varchar(32) COLLATE utf8_unicode_520_ci NOT NULL,
  `DESCRIBES` varchar(2000) COLLATE utf8_unicode_520_ci NOT NULL,
  `PREDICATES` varchar(2000) COLLATE utf8_unicode_520_ci NOT NULL,
  `FILTERS` varchar(2000) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `URI` varchar(100) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `ORDERS` int(11) DEFAULT '0',
  `UPSTREAM` varchar(100) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `DOWNSTREAM` varchar(100) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `UPDATE_TIME` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;  
当然可以做更多的拓展，主要的东西其实就是predicates和filters两大项，把这两项搞清楚，一切都很简单了。  

3、通过jar包：spring-boot-starter-actuator 完成监控查看路由信息及时时刷新  
（1）localhost:8666/actuator/gateway/routes查看路由配置信息  
（2）localhost:8666/actuator/gateway/refresh 更改数据库的路由配置后，请求改地址，完成刷新同步

4、predicates和filters功能应用的开源文档，如果单做简单的路由，基本简单配置下就够了。  
（1）filters：  
http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html#_gatewayfilter_factories

（2）predicates:  
http://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.0.0.RELEASE/single/spring-cloud-gateway.html#gateway-request-predicates-factories



