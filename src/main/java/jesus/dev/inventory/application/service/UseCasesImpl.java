package jesus.dev.inventory.application.service;

import jesus.dev.inventory.application.exception.NotFoundException;
import jesus.dev.inventory.application.exception.StatusAlreadySetException;
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
                .map(mapper::dtoToDomain)
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving cpu: {}", e.getMessage(), e);
                    return new RuntimeException("Error saving cpu: " + e.getMessage());
                });
    }

    @Override
    public Mono<Cpu> updateCpu(String id, CpuRequestDTO cpuRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cannot find cpu with id: " + id)))
                .flatMap(existingCpu -> Mono.fromSupplier(() -> {
                    Cpu cpu = mapper.dtoToDomain(cpuRequestDTO);
                    cpu.setId(existingCpu.getId());
                    cpu.setStatus(true);
                    return cpu;
                }))
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error updating cpu: {}", e.getMessage(), e);
                    return new RuntimeException("Error updating cpu: " + e.getMessage());
                });
    }


    @Override
    public Mono<Void> changeStatusCpu(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Cannot find cpu with id: " + id)))
                .flatMap(cpu -> {
                    if (cpu.getStatus().equals(status)) {
                        return Mono.error(new StatusAlreadySetException("CPU is already in the requested status: " + id));
                    }
                    return repository.changeStatus(cpu.getId(), status);
                })
                .onErrorMap(e -> {
                    log.error("Error updating cpu: {}", e.getMessage(), e);
                    return new RuntimeException("Error updating cpu: " + e.getMessage());
                });
    }

    @Override
    public Mono<Cpu> getCpuById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Cannot find cpu with id: " + id)));
    }

    @Override
    public Flux<Cpu> getCpuByStatus(Boolean status) {
        return repository.findByStatus(status)
                .switchIfEmpty(Mono.error(new NotFoundException("Cannot find cpu with status: " + status)));
    }

    @Override
    public Flux<Cpu> getCpuByIpAddress(String ipAddress) {
        return repository.findByIpAddress(ipAddress)
                .switchIfEmpty(Mono.error(new NotFoundException("Cannot find cpu with ip address: " + ipAddress)));
    }
}
