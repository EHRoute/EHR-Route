package ehroute.accountservice.configuration.thirdparty;
import org.fluentd.logger.FluentLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FluentdConfig {

    @Bean
    public FluentLogger fluentLogger() {
        return FluentLogger.getLogger("ehroute", "172.18.0.1", 24224);
    }

}
