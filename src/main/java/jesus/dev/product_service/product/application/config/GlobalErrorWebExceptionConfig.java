package jesus.dev.product_service.product.application.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalErrorWebExceptionConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
