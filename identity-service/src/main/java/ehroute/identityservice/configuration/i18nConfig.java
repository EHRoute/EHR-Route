package ehroute.identityservice.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import lombok.Data;

@Configuration
public class i18nConfig {
    
    
    /**
     * Message source used for i18n based on messages properties files
     * 
     * @return ReloadableResourceBundleMessageSource
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
