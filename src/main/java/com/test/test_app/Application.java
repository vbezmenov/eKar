package com.test.test_app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.singleton;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.test.test_app.repository")
@SpringBootApplication
@ComponentScan("com.test.test_app")
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.run(args);
    }

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.test_app.mvc"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .produces(singleton(APPLICATION_JSON_VALUE));
    }

}
