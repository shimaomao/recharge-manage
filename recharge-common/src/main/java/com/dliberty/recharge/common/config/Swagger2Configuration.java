package com.dliberty.recharge.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

	@Bean
	public Docket docket() {

		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.parameterType("header").name("Authorization")
				.defaultValue(
						"BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE1NzI2NjEwMDU2MTgsImV4cCI6MTU3Mjc0NzQwNX0.q8wH38JD28Kmkm3HwA7akmpTxB8p_elBI3lnzGcHLddSBIrXWTOIGa0RCpKxMz_PTcllH7p-mCAUkicuY43V7w")
				.description("header中字段username测试").modelRef(new ModelRef("string"))// 指定参数值的类型
				.required(false).build(); // 非必需，这里是全局配置

		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
				.globalOperationParameters(aParameters);
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("利用swagger2构建的API文档").description("用restful风格写接口").termsOfServiceUrl("")
				.version("1.0").build();
	}
}