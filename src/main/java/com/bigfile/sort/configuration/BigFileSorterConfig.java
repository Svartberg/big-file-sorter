package com.bigfile.sort.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bigfile.sorter")
public class BigFileSorterConfig {

    private String splitFilePath;

}
