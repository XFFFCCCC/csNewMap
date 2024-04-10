package com.pcl.healthism.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SpringBootApplication是一个复合注解，里头包含扫描配置和扫描package并初始化注解类
 * 默认springboot会扫描该标记所在的类的package和子package，可以通过scanBasePackages指定多个包
 * */
@SpringBootApplication
@MapperScan("com.pcl.healthism.dao.mapper")
@ComponentScan(basePackages = "com.pcl.healthism")
//@ServletComponentScan(basePackages = "com.pcl.healthism.web.controller.servlet")
public class RobotApplication {
	private final static String FILE_PATTERN = "classpath:application*.yml";



	public static void main(String[] args) {
		new SpringApplicationBuilder(RobotApplication.class)
				.properties(loadApplicationLocations())
				.build()
				.run(args);
	}


	/**
	 * 自定义的应用配置加载器，方便线上分环境配置,很多配置过大时则可以通过该方法来拆分一个application
	 * 文件为多个，方便配置管理
	 * 我们可以在resource目录下的config文件下自定义命名为application-*.yml文件作为应用启动时的初始化配置
	*/
	 private static String loadApplicationLocations() {
		String prefix = "spring.config.additional-location=";
		return prefix + String.join(",", loadSelfDefProperties());
	}

	private static List<String> loadSelfDefProperties() {
		try {
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(FILE_PATTERN);
			if (resources.length == 0) {
				return Collections.EMPTY_LIST;
			}
			return Stream.of(resources).map(l -> "classpath:"+ l.getFilename()).collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
