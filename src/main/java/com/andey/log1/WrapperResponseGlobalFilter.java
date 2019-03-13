//package com.andey.log1;
//
///**
// * Created by jiangbin on 2019/1/31.
// */
//
//
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {
//    @Override
//    public int getOrder() {
////-1 is response write filter, must be called before that
//        return -2;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        StringBuffer logBuffer = new StringBuffer();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//       //获取response的 返回数据
//        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
//        String URIPath = request.getURI().toString();
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        HttpHeaders header = originalResponse.getHeaders();
//        MediaType contentType=  header.getContentType();
//        InetSocketAddress address=header.getHost();
//        List<Charset> acceptCharset=header.getAcceptCharset();
//        originalResponse.getStatusCode();
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
//                    return super.writeWith(fluxBody.map(dataBuffer -> {
//                        byte[] content = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(content);
//                        //释放掉内存
//                        DataBufferUtils.release(dataBuffer);
//                        //responseData就是下游系统返回的内容,可以查看修改
//                        String responseData =new String(content, StandardCharsets.UTF_8);
//                        logBuffer.append('\n');
//                        logBuffer.append("原始请求-Response：").append(URIPath).append('\n');
//                        logBuffer.append("-------------响应头------------\n");
//                        logBuffer.append("Header:").append(header).append('\n');
//                        logBuffer.append("-------------响应body------------\n");
//                        logBuffer.append(responseData).append('\n');
//                        logBuffer.append("-------"+sdf.format(new Date())+"-------\n");
//                        log.info(logBuffer.toString());
//                        return bufferFactory.wrap(content);
//                    }));
//                } else {
//                    log.error("响应code异常:{}", getStatusCode());
//                }
//                return super.writeWith(body);
//            }
//        };
//        return chain.filter(exchange.mutate().response(decoratedResponse).build());
//    }
//}
