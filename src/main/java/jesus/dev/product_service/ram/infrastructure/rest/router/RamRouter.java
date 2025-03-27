package jesus.dev.product_service.ram.infrastructure.rest.router;

import jesus.dev.product_service.ram.infrastructure.rest.handler.RamHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RamRouter {
    private static final String BASE_PATH = "/api/v1/ram";

    @Bean
    public RouterFunction<ServerResponse> ramRoutes(RamHandler handler) {
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
