package no.fdk.dataservicecatalog.utils;

import no.fdk.dataservicecatalog.dto.shared.apispecification.info.Contact;
import no.fdk.dataservicecatalog.model.DataService;

import java.util.List;
import java.util.Map;

public class TestData {
    public static List<DataService> createDataServices(String organizationId) {
        return List.of(
                DataService
                        .builder()
                        .id(String.format("%s/id-1", organizationId))
                        .organizationId(organizationId)
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-2", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-2-title-en")
                                )
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-3", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-3-title-en"),
                                        Map.entry("nb", "id-3-title-nb")
                                )
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-4", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-4-title-en"),
                                        Map.entry("nb", "id-4-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-4-description-en")
                                )
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-5", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-5-title-en"),
                                        Map.entry("nb", "id-5-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-5-description-en"),
                                        Map.entry("nb", "id-5-description-nb")
                                )
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-6", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-6-title-en"),
                                        Map.entry("nb", "id-6-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-6-description-en"),
                                        Map.entry("nb", "id-6-description-nb")
                                )
                        )
                        .endpointDescriptions(List.of("http://endpoint-description-6"))
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-7", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-7-title-en"),
                                        Map.entry("nb", "id-7-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-7-description-en"),
                                        Map.entry("nb", "id-7-description-nb")
                                )
                        )
                        .endpointDescriptions(List.of("http://endpoint-description-7"))
                        .endpointUrl("http://endpoint-url-7")
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-8", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-8-title-en"),
                                        Map.entry("nb", "id-8-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-8-description-en"),
                                        Map.entry("nb", "id-8-description-nb")
                                )
                        )
                        .endpointDescriptions(List.of("http://endpoint-description-8"))
                        .endpointUrl("http://endpoint-url-8")
                        .contact(
                                Contact
                                        .builder()
                                        .name("organisation-name-8")
                                        .build()
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-9", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-9-title-en"),
                                        Map.entry("nb", "id-9-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-9-description-en"),
                                        Map.entry("nb", "id-9-description-nb")
                                )
                        )
                        .endpointDescriptions(List.of("http://endpoint-description-9-1", "http://endpoint-description-9-2", "http://endpoint-description-9-3"))
                        .endpointUrl("http://endpoint-url-9")
                        .contact(
                                Contact
                                        .builder()
                                        .name("organisation-name-9")
                                        .email("email-9@email.email")
                                        .build()
                        )
                        .build(),
                DataService
                        .builder()
                        .id(String.format("%s/id-10", organizationId))
                        .organizationId(organizationId)
                        .title(
                                Map.ofEntries(
                                        Map.entry("en", "id-10-title-en"),
                                        Map.entry("nb", "id-10-title-nb")
                                )
                        )
                        .description(
                                Map.ofEntries(
                                        Map.entry("en", "id-10-description-en"),
                                        Map.entry("nb", "id-10-description-nb")
                                )
                        )
                        .endpointDescriptions(List.of("http://endpoint-description-10"))
                        .endpointUrl("http://endpoint-url-10")
                        .contact(
                                Contact
                                        .builder()
                                        .name("organisation-name-10")
                                        .email("email-10@email.email")
                                        .url("http://organisation-url-10")
                                        .build()
                        )
                        .build()
        );
    }
}