package dev.jeppu.catalogservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "catalog")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationProperties {
    private Integer pageSize;
}
