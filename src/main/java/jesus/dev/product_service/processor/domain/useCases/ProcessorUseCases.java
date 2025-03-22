package jesus.dev.product_service.processor.domain.useCases;

import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.domain.model.dto.ProcessorRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProcessorUseCases {
    Mono<Processor> saveProcessor(ProcessorRequestDTO processorRequestDTO);

    Mono<Processor> updateProcessor(String id, ProcessorRequestDTO processorRequestDTO);

    Mono<Void> changeStatusProcessor(String id, Boolean status);

    Mono<Processor> getProcessorById(String id);

    Flux<Processor> getProcessorsByStatus(Boolean status);
}
