package ehroute.identityservice.configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogDatabaseConfig {

    @Value("${LOG_DB_URL}") private String logDbUrl;
    @Value("${LOG_DB_TOKEN}") private String logDbToken;


    @Bean
    public InfluxDBClient logDb() {
        return InfluxDBClientFactory.create(logDbUrl, logDbToken.toCharArray());
    }

}
