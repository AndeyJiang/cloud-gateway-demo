package com.andey;


import com.andey.config.redisConfig.Md5PasswordEncoder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@SpringBootApplication
@MapperScan("com.andey.dao")
public class SpringCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApplication.class, args);
	}


	/**
	 * IP限流
	 * @return
	 */
	@Bean
	KeyResolver addressKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostName());
	}

	/**
	 * 接口限流
	 * @return
	 */
	@Bean
	KeyResolver pathKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}

	/**
	 * 用户ID限流
	 */
	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
	}

	/**
	 *
	 * Md5加密
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();
	}


}

