package jesus.dev.inventory.application.service;

import jesus.dev.inventory.application.exception.ItemCreationException;
import jesus.dev.inventory.application.exception.ItemNotFoundException;
import jesus.dev.inventory.application.exception.ItemStatusAlreadySetException;
import jesus.dev.inventory.application.exception.ItemUpdatedException;
import jesus.dev.inventory.application.mapper.CpuUseCaseMapper;
import jesus.dev.inventory.domain.model.Cpu;
import jesus.dev.inventory.domain.model.dto.CpuRequestDTO;
import jesus.dev.inventory.domain.repository.CpuRepository;
import jesus.dev.inventory.domain.useCases.CpuUseCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UseCasesImpl implements CpuUseCases {
    private final CpuRepository repository;
    private final CpuUseCaseMapper mapper;

    @Override
    public Mono<Cpu> saveCpu(CpuRequestDTO cpuRequestDTO) {
        return Mono.just(cpuRequestDTO)
                .map(dto -> {
                    Cpu cpu = mapper.dtoToDomain(dto);
                    cpu.setStatus(true);
                    return cpu;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving cpu: {}", e.getMessage(), e);
                    return new ItemCreationException("Error saving cpu: ", e);
                });
    }

    @Override
    public Mono<Cpu> updateCpu(String id, CpuRequestDTO cpuRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cpu not found with id: " + id)))
                .flatMap(existingCpu -> Mono.fromSupplier(() -> {
                    Cpu cpu = mapper.dtoToDomain(cpuRequestDTO);
                    cpu.setId(existingCpu.getId());
                    cpu.setStatus(true);
                    return cpu;
                }))
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException) {
                        return e;
                    }
                    return new ItemUpdatedException("Error saving cpu: ", e);
                });
    }


    @Override
    public Mono<Void> changeStatusCpu(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cannot find cpu with id: " + id)))
                .flatMap(cpu -> {
                    if (cpu.getStatus().equals(status)) {
                        return Mono.error(new ItemStatusAlreadySetException("CPU is already in the requested status: " + id));
                    }
                    return repository.changeStatus(cpu.getId(), status);
                })
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException || e instanceof ItemStatusAlreadySetException) {
                        return e;
                    }
                    log.error("Error updating cpu: {}", e.getMessage(), e);
                    return new ItemUpdatedException("Error updating cpu: ", e);
                });
    }

    @Override
    public Mono<Cpu> getCpuById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cannot find cpu with id: " + id)));
    }

    @Override
    public Flux<Cpu> getCpuByStatus(Boolean status) {
        return repository.findByStatus(status)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cannot find cpu with status: " + status)));
    }

    @Override
    public Flux<Cpu> getCpuByIpAddress(String ipAddress) {
        return repository.findByIpAddress(ipAddress)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cannot find cpu with ip address: " + ipAddress)));
    }
}
