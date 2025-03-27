package jesus.dev.product_service.screen.domain.repository;

import jesus.dev.product_service.screen.domain.model.Screen;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScreenRepository {
    Mono<Screen> save(Screen screen);

    Mono<Void> changeStatus(String id, Boolean status);

    Mono<Screen> findById(String id);

    Flux<Screen> findByStatus(Boolean status);
}
