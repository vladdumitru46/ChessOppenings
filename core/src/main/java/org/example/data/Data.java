package org.example.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom")
public class Data {
    private int numberOfThreads;
    private int depthForAi;
    private String emailFrom;
}
