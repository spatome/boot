package com.spatome.boot.netty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "my.netty")
@Getter
@Setter
public class NettyConfig {

	private String host;
	private int port;

}
