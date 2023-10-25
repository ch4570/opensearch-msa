package analytics.main.service;

import analytics.main.domain.entity.RequestInfo;
import analytics.main.repository.RequestInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestInfoService {

    private final RequestInfoRepository repository;

    public void saveRequestLog(RequestInfo requestInfo) {
        repository.save(requestInfo).subscribe();
    }
}
