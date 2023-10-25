package analytics.main.repository;

import analytics.main.domain.entity.RequestInfo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RequestInfoRepository extends R2dbcRepository<RequestInfo, Long> {
}
