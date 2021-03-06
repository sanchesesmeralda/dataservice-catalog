package no.fdk.dataservicecatalog.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("security")
public class SecurityProperties {
    private String jwksUri;
}
