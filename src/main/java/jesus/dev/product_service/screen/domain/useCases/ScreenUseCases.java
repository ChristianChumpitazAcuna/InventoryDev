package jesus.dev.product_service.screen.domain.useCases;

import jesus.dev.product_service.screen.domain.model.Screen;
import jesus.dev.product_service.screen.domain.model.dto.ScreenRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScreenUseCases {
    Mono<Screen> saveScreen(ScreenRequestDTO screenRequestDTO);

    Mono<Screen> updateScreen(String id, ScreenRequestDTO screenRequestDTO);

    Mono<Void> changeStatusScreen(String id, Boolean status);

    Mono<Screen> getScreenById(String id);

    Flux<Screen> getScreenByStatus(Boolean status);
}
