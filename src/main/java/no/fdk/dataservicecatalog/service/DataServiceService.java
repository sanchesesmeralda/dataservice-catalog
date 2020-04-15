package no.fdk.dataservicecatalog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.fdk.dataservicecatalog.dto.shared.apispecification.ApiSpecification;
import no.fdk.dataservicecatalog.dto.shared.apispecification.ApiSpecificationSource;
import no.fdk.dataservicecatalog.exceptions.NotFoundException;
import no.fdk.dataservicecatalog.model.DataService;
import no.fdk.dataservicecatalog.repository.DataServiceMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataServiceService {

    private final ApiHarvesterReactiveClient apiHarvesterReactiveClient;
    private final DataServiceMongoRepository dataServiceMongoRepository;

    private static Map<String, String> setDefaultLanguageValue(String value) {
        return Collections.singletonMap(DataService.DEFAULT_LANGUAGE, value);
    }

    private DataService parseApiSpecification(ApiSpecification apiSpecification) {
        var apiInfo = apiSpecification.getInfo();
        var paths = apiSpecification.getPaths();

        return DataService.builder()
                .license(apiInfo.getLicense())
                .title(setDefaultLanguageValue(apiInfo.getTitle()))
                .description(setDefaultLanguageValue(apiInfo.getDescription()))
                .version(apiInfo.getVersion())
                .operationCount(paths.size())
                .contact(apiInfo.getContact())
                .build();

    }

    public Mono<DataService> importFromSpecification(ApiSpecificationSource source, String catalogId) {
        Mono<ApiSpecification> apiSpecification = apiHarvesterReactiveClient.convertApiSpecification(source);
        Mono<DataService> dataServiceMono = apiSpecification.map(this::parseApiSpecification)
                .doOnSuccess(dataService -> log.debug("dataservice loaded from specification"))
                .doOnError(error -> log.error("new dataservice failed mapping: {}", error.getMessage()));
        return dataServiceMono.flatMap(dataService -> {
            dataService.setOrganizationId(catalogId);
            dataService.setStatus("DRAFT");
            return dataServiceMongoRepository.save(dataService);
        });
    }

    public Mono<DataService> importFromSpecification(String dataServiceId, String catalogId, ApiSpecificationSource source) {
        Mono<ApiSpecification> apiSpecification = apiHarvesterReactiveClient.convertApiSpecification(source);
        Mono<DataService> dataServiceMono = apiSpecification.map(this::parseApiSpecification)
                .doOnSuccess(dataService -> log.debug("dataservice {} loaded from specification", dataService.getId()))
                .doOnError(error -> log.error("dataservice with id {} failed mapping {}", dataServiceId, error.getMessage()));
        return dataServiceMono.flatMap(dataService -> {
            dataService.setId(dataServiceId);
            dataService.setOrganizationId(catalogId);
            return dataServiceMongoRepository.save(dataService);
        });
    }

    public Flux<DataService> getAllDataServices(String catalogId) {
        var all = dataServiceMongoRepository.findAllByOrganizationId(catalogId)
                .doOnError(error -> log.error("error retrieving all dataservices from mongo: {}", error.getMessage()));
        all.count().subscribe(count -> log.debug("found {} dataservices", count));
        return all;
    }

    public Mono<DataService> create(DataService dataService, String catalogId) {
        dataService.setOrganizationId(catalogId);
        return dataServiceMongoRepository.save(dataService)
                .doOnSuccess(saved -> log.debug("dataservice {} saved", saved.getId()))
                .doOnError(error -> log.error("error saving dataservice to database: {}", error.getMessage()));
    }

    public Mono<DataService> findById(String dataServiceId, String catalogId) {
        return dataServiceMongoRepository.findByIdAndOrganizationId(dataServiceId, catalogId)
                .doOnSuccess(dataService -> {
                    if (dataService != null) {
                        log.debug("dataservice {} retrieved", dataService.getId());
                    } else {
                        log.error("no dataservice with id {} exists for catalog {}", dataServiceId, catalogId);
                    }
                })
                .doOnError(error -> log.error("error retrieving dataservice {}: {}", dataServiceId, error.getMessage()));
    }

    public Mono<Boolean> deleteById(String dataServiceId, String catalogId) {
        return dataServiceMongoRepository.deleteByIdAndOrganizationId(dataServiceId, catalogId)
                .doOnError(error -> log.error("error deleting dataservice {}: {}", dataServiceId, error.getMessage()))
                .map(deletedCount -> deletedCount > 0)
                .doOnSuccess(deleted -> log.debug("dataset {} deleted: {}", dataServiceId, deleted));
    }

    public Mono<DataService> update(String dataServiceId, String catalogId, DataService updated) {
        return dataServiceMongoRepository.findByIdAndOrganizationId(dataServiceId, catalogId)
                .doOnError(error -> log.error("error retrieving dataservice {}: {}", dataServiceId, error.getMessage()))
                .flatMap(dataService -> {
                    if (dataService != null) {
                        log.debug("dataservice {} retrieved for patch", dataService.getId());
                        updated.setId(dataServiceId);
                        return dataServiceMongoRepository.save(updated);
                    }
                    log.error("no dataservice with id {} exists for catalog {}", dataServiceId, catalogId);
                    return Mono.error(new NotFoundException("no dataservice found"));
                });
    }
}
