//package com.andey.log;
//
//import org.reactivestreams.Publisher;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by jiangbin on 2019/1/29.
// */
//public class RecorderServerHttpResponseDecorator extends ServerHttpResponseDecorator{
//
//    private final List<DataBuffer> dataBuffers = new LinkedList<>();
//
//    public RecorderServerHttpResponseDecorator(ServerHttpResponse delegate) {
//        super(delegate);
//    }
//
//    public Flux<DataBuffer> copy() {
//        return Flux.fromIterable(dataBuffers)
//                .map(buf -> buf.factory().wrap(buf.asByteBuffer()));
//    }
//
//    @Override
//    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//
//        return getDelegate().writeWith(body);
//    }
//}
