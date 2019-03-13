//package com.andey.log3;
//
//import io.netty.buffer.ByteBufAllocator;
//import org.reactivestreams.Publisher;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.core.io.buffer.DataBufferUtils;
//import org.springframework.core.io.buffer.NettyDataBufferFactory;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.nio.CharBuffer;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * Created by jiangbin on 2019/2/1.
// */
//@Component
//public class AuthGlobalFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public int getOrder(){
//        return -2;
//    }
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        URI uri = exchange.getRequest().getURI();
//        try {
//            System.out.println(uri.toURL());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        ServerHttpRequest request= exchange.getRequest();
//        if("POST".equalsIgnoreCase(request.getMethodValue())) {
//            //判断是否为POST请求
//            Flux<DataBuffer> body = request.getBody();
//            AtomicReference<String> bodyRef = new AtomicReference<>();//缓存读取的request body信息
//            body.subscribe(dataBuffer -> {
//                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
//                DataBufferUtils.release(dataBuffer);
//                bodyRef.set(charBuffer.toString());
//            });//读取request body到缓存
//            String bodyStr = bodyRef.get();//获取request body
//            System.out.println("-----------------request body start-----------------");
//            System.out.println(bodyStr);//这里是我们需要做的操作
//            System.out.println("-----------------request body end-----------------");
//            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
//            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//
//            request = new ServerHttpRequestDecorator(request) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return bodyFlux;
//                }
//            };//封装我们的request
//        }
//
//
//        ServerHttpResponse originalResponse = exchange.getResponse();
//
//        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    return super.writeWith(fluxBody.map(dataBuffer -> {
//                        // probably should reuse buffers
//                        byte[] content = new byte[dataBuffer.readableByteCount()];
//                        dataBuffer.read(content);
//                        //释放掉内存
//                        DataBufferUtils.release(dataBuffer);
//                        String s = new String(content, Charset.forName("UTF-8"));
//                        System.out.println("-----------------response body start-----------------");
//                        System.out.println(s);
//                        System.out.println("-----------------response body end-----------------");
//                        //TODO，s就是response的值，想修改、查看就随意而为了
//                        byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
//                        return bufferFactory.wrap(uppedContent);
//                    }));
//                }
//                // if body is not a flux. never got there.
//                return super.writeWith(body);
//            }
//        };
//        // replace response with decorator
//        return chain.filter(exchange.mutate().request(request).response(decoratedResponse).build());
//    }
//
//    protected DataBuffer stringBuffer(String value) {
//        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
//
//        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
//        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
//        buffer.write(bytes);
//        return buffer;
//    }
//}
//
