package com.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Nir.
 */
@Data
@Component
// PropertySource together with ConfigurationProperties does not works with yml files
// see https://github.com/spring-projects/spring-boot/issues/9104
@PropertySource("classpath:mime-message-properties.properties")
@ConfigurationProperties(prefix = "com.mime.message")
@Configuration
public class MimeMessageProperties {

    private int multipartMode;
    private boolean textHtmlContentType;

}
