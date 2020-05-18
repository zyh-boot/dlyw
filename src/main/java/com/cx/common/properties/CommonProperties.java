package com.cx.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Administrator·
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:common.properties", "classpath:spy.properties"})
@ConfigurationProperties(prefix = "cx")
public class CommonProperties{

    private SecurityProperties security = new SecurityProperties();
    private boolean openAopLog = true;
    private boolean isVerify = true;
    private SwaggerProperties swagger = new SwaggerProperties();

}
