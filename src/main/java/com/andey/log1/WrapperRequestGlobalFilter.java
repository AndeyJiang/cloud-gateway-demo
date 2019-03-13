//package com.andey.log1;
//
///**
// * Created by jiangbin on 2019/1/31.
// */
//
//import java.net.URI;
//import java.nio.CharBuffer;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.atomic.AtomicReference;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.NettyDataBufferFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import io.netty.buffer.ByteBufAllocator;
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
//
///**
// *  * 在filter中获取前置预言里面的请求body
// *  
// */
//@Component
//public class WrapperRequestGlobalFilter implements GlobalFilter, Ordered {
//    /**
//     *      * 优先级最高
//     *      
//     */
//    private Logger logger = LoggerFactory.getLogger(WrapperRequestGlobalFilter.class);
//
//    private final static String REQUEST_RECORDER_LOG_BUFFER = "RequestRecorderGlobalFilter.request_recorder_log_buffer";
//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        StringBuffer logBuffer = new StringBuffer();
//        Route gatewayUrl = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
//        URI uri = gatewayUrl.getUri();
//        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
//        //ServerHttpRequest.Builder mutate = request.mutate();
//        String URIPath = request.getURI().toString();
//        String path = request.getPath().value();
//        String method = request.getMethodValue();
//        String instance = uri.getAuthority();
//        HttpHeaders header = request.getHeaders();
//        logBuffer.append('\n');
//        logBuffer.append("原始请求-Request：").append(URIPath).append('\n');
//        logBuffer.append("-------------请求头------------\n");
//        logBuffer.append("uri:").append(uri).append('\n');
////        logBuffer.append("URIPath:").append(URIPath).append('\n');
//        logBuffer.append("path:").append(path).append('\n');
//        logBuffer.append("method:").append(method).append('\n');
//        logBuffer.append("instance:").append(instance).append('\n');
//        logBuffer.append("header:").append(header).append('\n');
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//        //缓存读取的request body信息
//        Flux<DataBuffer> fluxBody = exchange.getRequest().getBody();
//        fluxBody.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            DataBufferUtils.release(buffer);
//            bodyRef.set(charBuffer.toString());
//        });
//        String bodyStr = bodyRef.get();
//        //获取request body
//        if("POST".equals(method)|| "post".equals(method)){
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            if(bodyStr!=null){
//                DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
//                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//                request = new ServerHttpRequestDecorator(request) {
//                    @Override
//                    public Flux<DataBuffer> getBody() {
//                        return bodyFlux;
//                    }
//                };
//                logBuffer.append("-------------请求body------------\n");
//                logBuffer.append("请求内容:").append(bodyStr).append('\n');
//
//                logBuffer.append("-------"+sdf.format(new Date())+"-------\n");
//            }else{
//                logBuffer.append("-------------请求body------------\n");
//                logBuffer.append("           null，不记录body\n");
//                logBuffer.append("-------"+sdf.format(new Date())+"-------\n");
//            }
//            logger.info(logBuffer.toString());
//        }
//        return chain.filter(exchange.mutate().request(request).build());
//    }
//
//    protected DataBuffer stringBuffer(String value) {
//        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
//        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
//        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
//        buffer.write(bytes);
//        return buffer;
//    }
//
//}
//
