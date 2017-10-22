package site.kenz.db.api.kenzdbapi;

import io.swagger.annotations.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"site.kenz.db.api"})
public class KenzDbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KenzDbApiApplication.class, args);
	}

	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了
	 * （访问页面就可以看到效果了）
	 *
	 */
	@Bean
	public Docket testApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("test")
				.genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
//				.paths(or(regex("/api/.*")))//过滤的接口
				.build()
				.apiInfo(testApiInfo());
	}

	@Bean
	public Docket demoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("demo")
				.genericModelSubstitutes(DeferredResult.class)
//              .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.pathMapping("/")
				.select()
//				.paths(or(regex("/demo/.*")))//过滤的接口
				.build()
				.apiInfo(demoApiInfo());
	}

	private ApiInfo testApiInfo() {
		return new ApiInfoBuilder()
				.title("Electronic Health Record(EHR) Platform API")//大标题
				.description("EHR Platform's REST API, all the applications could access the Object model data via JSON.")//详细描述
				.version("1.0")//版本
				.termsOfServiceUrl("NO terms of service")
//				.contact(new Contact("小单", "http://blog.csdn.net/catoop", "365384722@qq.com"))//作者
				.license("The Apache License, Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}

	private ApiInfo demoApiInfo() {
		return new ApiInfoBuilder()
				.title("Electronic Health Record(EHR) Platform API")//大标题
				.description("EHR Platform's REST API, all the applications could access the Object model data via JSON.")//详细描述
				.version("1.0")//版本
				.termsOfServiceUrl("NO terms of service")
//				.contact(new Contact("小单", "http://blog.csdn.net/catoop", "365384722@qq.com"))//作者
				.license("The Apache License, Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}
}
