package gov.cdc.nbs.questionbank.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Docket api() {
        // Add auth header to all routes
        List<RequestParameter> parameters = new ArrayList<>();
        RequestParameterBuilder aParameterBuilder = new RequestParameterBuilder();
        aParameterBuilder.name("Authorization").in(ParameterType.HEADER).required(true);
        parameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("gov.cdc.nbs.questionbank"))
                .build()
                .globalRequestParameters(parameters);
    }

}
