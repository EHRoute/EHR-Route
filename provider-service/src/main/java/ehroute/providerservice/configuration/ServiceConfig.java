package ehroute.providerservice.configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import lombok.Data;


@Component
@EnableConfigurationProperties
@ConfigurationProperties("provider-service")
@RefreshScope
@Data
public class ServiceConfig {
	private String TestMessage;
}
