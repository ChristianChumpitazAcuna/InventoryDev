package jesus.dev.product_service.ram.application.service;

import jesus.dev.product_service.exception.ItemCreationException;
import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.ItemStatusAlreadySetException;
import jesus.dev.product_service.exception.ItemUpdatedException;
import jesus.dev.product_service.ram.application.mapper.RamUseCaseMapper;
import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.domain.model.dto.RamRequestDTO;
import jesus.dev.product_service.ram.domain.repository.RamRepository;
import jesus.dev.product_service.ram.domain.useCases.RamUseCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UseCasesImpl implements RamUseCases {
    private final RamRepository repository;
    private final RamUseCaseMapper mapper;

    @Override
    public Mono<Ram> saveRam(RamRequestDTO ramRequestDTO) {
        return Mono.just(ramRequestDTO)
                .map(dto -> {
                    Ram ram = mapper.dtoToDomain(dto);
                    ram.setStatus(true);
                    return ram;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving ram: {}", e.getMessage());
                    return new ItemCreationException("Error saving ram");
                });
    }

    @Override
    public Mono<Ram> updateRam(String id, RamRequestDTO ramRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Ram not found with id: " + id)))
                .map(existingRam -> {
                    Ram ram = mapper.dtoToDomain(ramRequestDTO);
                    ram.setId(existingRam.getId());
                    ram.setStatus(existingRam.getStatus());
                    return ram;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException) {
                        return e;
                    }
                    log.error("Error updating ram: {}", e.getMessage());
                    return new ItemUpdatedException("Error updating ram");
                });
    }

    @Override
    public Mono<Void> changeStatusRam(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Ram not found with id: " + id)))
                .flatMap(ram -> {
                    if (ram.getStatus().equals(status)) {
                        return Mono.error(new ItemStatusAlreadySetException("Ram is already in the requested status: " + id));
                    }
                    return repository.changeStatus(id, status);
                })
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException || e instanceof ItemStatusAlreadySetException) {
                        return e;
                    }
                    log.error("Error changing status ram: {}", e.getMessage());
                    return new ItemNotFoundException("Error changing status ram");
                });
    }

    @Override
    public Mono<Ram> getRamById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Ram not found with id: " + id)));
    }

    @Override
    public Flux<Ram> getRamByStatus(Boolean status) {
        return repository.findByStatus(status);
    }
}
