package jesus.dev.inventory.domain.useCases;

import jesus.dev.inventory.domain.model.Cpu;
import jesus.dev.inventory.domain.model.dto.CpuRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CpuUseCases {
    Mono<Cpu> saveCpu(CpuRequestDTO cpuRequestDTO);

    Mono<Cpu> updateCpu(String id, CpuRequestDTO cpuRequestDTO);

    Mono<Void> changeStatusCpu(String id, Boolean status);

    Mono<Cpu> getCpuById(String id);

    Flux<Cpu> getCpuByStatus(Boolean status);

    Flux<Cpu> getCpuByIpAddress(String ipAddress);
}
