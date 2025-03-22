package jesus.dev.product_service.product.infraestructure.rest.router;

import jesus.dev.product_service.product.infraestructure.rest.handler.CpuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CpuRouter {
    private static final String BASE_PATH = "/api/v1/cpu";

    @Bean
    public RouterFunction<ServerResponse> cpuRoutes(CpuHandler handler) {
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
