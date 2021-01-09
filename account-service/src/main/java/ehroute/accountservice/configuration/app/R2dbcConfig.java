package ehroute.accountservice.configuration.app;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;


@Configuration
@EnableR2dbcAuditing
@EnableR2dbcRepositories
// @EnableWebFlux
public class R2dbcConfig /*extends AbstractR2dbcConfiguration*/ {

    //@Autowired private ConnectionFactory connectionFactory;

    /*@Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }*/

    // TODO: This
    /*@Bean
    public MappingR2dbcConverter mappingR2dbcConverter() {
        return new MappingR2dbcConverter();
    }*/

}
