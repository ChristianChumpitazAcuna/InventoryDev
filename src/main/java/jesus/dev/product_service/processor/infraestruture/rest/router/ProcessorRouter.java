package jesus.dev.product_service.processor.infraestruture.rest.router;

import jesus.dev.product_service.processor.infraestruture.rest.handler.ProcessorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProcessorRouter {
    private static final String BASE_PATH = "/api/v1/processor";

    @Bean
    public RouterFunction<ServerResponse> cpuRoutes(ProcessorHandler handler) {
        return RouterFunctions
                .nest(path(BASE_PATH),
                        route(POST("/create"), handler::create)
                                .andRoute(PUT("/update/{id}"), handler::update)
                                .andRoute(PATCH("/changeStatus/{status}/{id}"), handler::changeStatus)
                                .andRoute(GET("/{id}"), handler::getById)
                                .andRoute(GET("/findByStatus/{status}"), handler::getByStatus)
                );
    }
}
