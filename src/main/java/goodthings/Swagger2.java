package goodthings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by tujl on 2018/12/14.
 */

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swagger.enable}")
    private boolean enableSwagger;
    @Value("${swagger.url}")
    private String url;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(url)
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("goodthings"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful APIs")
                .description("")
//                .termsOfServiceUrl("http://blog.didispace.com/")
//                .contact("程序猿DD")
                .version("1.0")
                .build();
    }

}
