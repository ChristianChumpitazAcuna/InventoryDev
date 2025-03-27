package jesus.dev.product_service.screen.application.service;

import jesus.dev.product_service.exception.ItemCreationException;
import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.ItemStatusAlreadySetException;
import jesus.dev.product_service.exception.ItemUpdatedException;
import jesus.dev.product_service.screen.application.mapper.ScreenUseCaseMapper;
import jesus.dev.product_service.screen.domain.model.Screen;
import jesus.dev.product_service.screen.domain.model.dto.ScreenRequestDTO;
import jesus.dev.product_service.screen.domain.repository.ScreenRepository;
import jesus.dev.product_service.screen.domain.useCases.ScreenUseCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScreenUseCaseImpl implements ScreenUseCases {
    private final ScreenRepository repository;
    private final ScreenUseCaseMapper mapper;

    @Override
    public Mono<Screen> saveScreen(ScreenRequestDTO screenRequestDTO) {
        return Mono.just(screenRequestDTO)
                .map(dto -> {
                    Screen screen = mapper.dtoToDomain(screenRequestDTO);
                    screen.setStatus(true);
                    return screen;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving screen: {}", e.getMessage());
                    return new ItemCreationException("Error saving screen");
                });
    }

    @Override
    public Mono<Screen> updateScreen(String id, ScreenRequestDTO screenRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Screen not found with id: " + id)))
                .map(existingScreen -> {
                    Screen screen = mapper.dtoToDomain(screenRequestDTO);
                    screen.setId(existingScreen.getId());
                    screen.setStatus(true);
                    return screen;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException) {
                        return e;
                    }
                    log.error("Error updating screen: {}", e.getMessage());
                    return new ItemUpdatedException("Error updating screen");
                });
    }

    @Override
    public Mono<Void> changeStatusScreen(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Screen not found with id: " + id)))
                .flatMap(screen -> {
                    if (screen.getStatus().equals(status)) {
                        return Mono.error(new ItemStatusAlreadySetException(
                                "Screen is already in the requested status: " + id));
                    }
                    return repository.changeStatus(id, status);
                })
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException || e instanceof ItemStatusAlreadySetException) {
                        return e;
                    }
                    log.error("Error chaining screen status: {}", e.getMessage());
                    return new ItemNotFoundException("Error chaining screen status");
                });
    }

    @Override
    public Mono<Screen> getScreenById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Screen not found with id: " + id)));
    }

    @Override
    public Flux<Screen> getScreenByStatus(Boolean status) {
        return repository.findByStatus(status);
    }
}
