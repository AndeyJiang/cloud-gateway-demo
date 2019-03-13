//package com.andey.service;
//
//import com.andey.dao.TGateWayRouteDefinitionMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
//import org.springframework.cloud.gateway.filter.FilterDefinition;
//import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.stereotype.Service;
//import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service("testGatewaySerrviceEvt")
//public class TestGatewayService implements ApplicationEventPublisherAware {
//
//    @Autowired
//    private RouteDefinitionWriter routeDefinitionWriter;
//    private ApplicationEventPublisher publisher;
//
//    @Autowired
//    TGateWayRouteDefinitionMapper tGateWayRouteDefinitionMapper;
//    public RouteDefinition testPath() {
//        RouteDefinition definition = new RouteDefinition();
//        PredicateDefinition predicate = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        PredicateDefinition predicate1 = new PredicateDefinition();
//        definition.setId("12345");
//        predicate.setName("Path");
//        predicateParams.put("pattern", "/111");
//        predicateParams.put("pathPattern", "/baidu");
//        URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com/").build().toUri();
//        definition.setUri(uri);
//        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
//        publisher.publishEvent(new RefreshRoutesEvent(this));
//        return definition;
//        //test(definition);
//    }
//    public void testRedis() {
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        URI uri = UriComponentsBuilder.fromUriString("lb://HELLO-SERVICE").build().toUri();
//        routeDefinition.setId("rateLimitTest");
//        // 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
//        predicateDefinition.setName("Path");
//        predicateParams.put("pattern", "/rate/**");
//        predicateDefinition.setArgs(predicateParams);
//        // 名称是固定的，spring gateway会根据名称找对应的FilterFactory
//        filterDefinition.setName("RequestRateLimiter");
//        // 每秒最大访问次数
//        filterParams.put("redis-rate-limiter.replenishRate", "2");
//        // 令牌桶最大容量
//        filterParams.put("redis-rate-limiter.burstCapacity", "3");
//        // 限流策略(#{@BeanName})
//        filterParams.put("key-resolver", "#{@hostAddressKeyResolver}");
//        // 自定义限流器(#{@BeanName})
//        //filterParams.put("rate-limiter", "#{@redisRateLimiter}");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//
//    }
//    public void testMethod(){
//        //MethodRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("Method");
//        predicateParams.put("method", "GET");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testQuery(){
//        //QueryRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("Query");
//        predicateParams.put("param", "f");
//        predicateParams.put("regexp", "ba.");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testAfter(){
//        //AfterRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("After");
//        predicateParams.put("datetime", "2017-01-20T17:42:47.789-07:00[America/Denver]");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//
//    public void testCookie(){
//        //CookieRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("Cookie");
//        predicateParams.put("name", "chocolate");
//        predicateParams.put("regexp", "ch.p");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testHeader(){
//        //HeaderRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("Header");
//        predicateParams.put("header", "X-Request-Id");
//        predicateParams.put("regexp", "\\d+");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testHost(){
//        //HostRoutePredicateFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//        predicateDefinition.setName("Header");
//        predicateParams.put("pattern", "**.somehost.org");
//        predicateDefinition.setArgs(predicateParams);
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//
//    public void testAddRequestHeaderFilter(){
//        //AddRequestHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("AddRequestHeader");
//        filterParams.put("name","X-Request-Foo");
//        filterParams.put("value","Bar");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testAddRequestParameterFilter(){
//        //AddRequestParameterGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("AddRequestParameter");
//        filterParams.put("name","foo");
//        filterParams.put("value","bar");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testAddResponseHeaderFilter(){
//        //AddResponseHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("AddResponseHeader");
//        filterParams.put("name","X-Response-Foo");
//        filterParams.put("value","bar");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testHystrixFilter(){
//        //HystrixGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("Hystrix");
//        filterParams.put("name","fallbackcmd");
//        filterParams.put("fallbackUri","forward:/incaseoffailureusethis");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testPrefixPathFilter(){
//        //AddResponseHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("PrefixPath");
//        filterParams.put("prefix","/mypath");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testPreserveHostHeaderFilter(){
//        //PreserveHostHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("PreserveHostHeader");
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testRedirectToFilter(){
//        //RedirectToGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("RedirectTo");
//        filterParams.put("status","302");
//        filterParams.put("url","http://acme.org");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testRemoveRequestHeaderFilter(){
//        //RemoveRequestHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("RemoveRequestHeader");
//        filterParams.put("name","X-Request-Foo");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testRemoveResponseHeaderFilter(){
//        //RemoveResponseHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("RemoveResponseHeader");
//        filterParams.put("name","X-Response-Foo");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testRewritePathFilter(){
//        //RewritePathGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("RewritePath");
//        filterParams.put("regexp","/$\\{segment}");
//        filterParams.put("replacement","/foo/(?<segment>.*)");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testSaveSessionFilter(){
//        //SaveSessionGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("SaveSession");
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testSetResponseHeaderFilter(){
//        //SetResponseHeaderGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("SetResponseHeader");
//        filterParams.put("name","X-Response-Foo");
//        filterParams.put("value","bar");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testSetStatusFilter(){
//        //SetStatusGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("SetStatus");
//        filterParams.put("status","401");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testStripPrefixFilter(){
//        //StripPrefixGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("StripPrefix");
//        filterParams.put("parts","2");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public void testRetryFilter(){
//        //RetryGatewayFilterFactory
//        RouteDefinition routeDefinition = new RouteDefinition();
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("Retry");
//        filterParams.put("retries","3");
//        filterParams.put("statuses","BAD_GATEWAY");
//        filterDefinition.setArgs(filterParams);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        URI uri = UriComponentsBuilder.fromUriString("http://example.org").build().toUri();
//        routeDefinition.setUri(uri);
//        test(routeDefinition);
//    }
//    public String test(RouteDefinition routeDefinition){
//        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
//        publisher.publishEvent(new RefreshRoutesEvent(this));
//        return "success";
//    }
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.publisher = applicationEventPublisher;
//    }
//}
