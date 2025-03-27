package jesus.dev.product_service.processor.application.service;

import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.ItemStatusAlreadySetException;
import jesus.dev.product_service.exception.ItemUpdatedException;
import jesus.dev.product_service.processor.application.mapper.ProcessorUseCaseMapper;
import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.domain.model.dto.ProcessorRequestDTO;
import jesus.dev.product_service.processor.domain.repository.ProcessorRepository;
import jesus.dev.product_service.processor.domain.useCases.ProcessorUseCases;
import jesus.dev.product_service.exception.ItemCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessorUseCaseImpl implements ProcessorUseCases {
    private final ProcessorRepository repository;
    private final ProcessorUseCaseMapper mapper;

    @Override
    public Mono<Processor> saveProcessor(ProcessorRequestDTO processorRequestDTO) {
        return Mono.just(processorRequestDTO)
                .map(dto -> {
                    Processor processor = mapper.dtoToDomain(dto);
                    processor.setStatus(true);
                    return processor;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving Processor: {}", e.getMessage(), e);
                    return new ItemCreationException("Error saving processor", e);
                });
    }

    @Override
    public Mono<Processor> updateProcessor(String id, ProcessorRequestDTO processorRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Processor not found with id: " + id)))
                .map(existingProcessor -> {
                    Processor processor = mapper.dtoToDomain(processorRequestDTO);
                    processor.setId(id);
                    processor.setStatus(true);
                    return processor;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException) {
                        return e;
                    }
                    log.error("Error updating Processor: {}", e.getMessage(), e);
                    return new ItemUpdatedException("Error updating processor", e);
                });

    }

    @Override
    public Mono<Void> changeStatusProcessor(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Processor not found with id: " + id)))
                .flatMap(processor -> {
                    if (processor.getStatus().equals(status)) {
                        return Mono.error(new ItemStatusAlreadySetException("Processor is already in the requested status: " + id));
                    }
                    return repository.changeStatus(processor.getId(), status);
                })
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException || e instanceof ItemStatusAlreadySetException) {
                        return e;
                    }
                    log.error("Error chaining Processor status: {}", e.getMessage(), e);
                    return new ItemUpdatedException("Error chaining Processor status", e);
                });
    }

    @Override
    public Mono<Processor> getProcessorById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Processor not found with id: " + id)));
    }

    @Override
    public Flux<Processor> getProcessorsByStatus(Boolean status) {
        return repository.findByStatus(status);
    }
}
