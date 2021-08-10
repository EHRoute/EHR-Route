package ehroute.identityservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import io.r2dbc.postgresql.*;
import io.r2dbc.spi.ConnectionFactory;


@Configuration
@ComponentScan({"com.muizz.spring.mediator.core"})
@EnableR2dbcAuditing
public class DatabaseConfig extends AbstractR2dbcConfiguration {


    @Value("${DB_NAME}") private String dbName;
    @Value("${DB_USER}") private String dbUser;
    @Value("${DB_PASSWORD}") private String dbPassword;


    
    /**
     * Creates an R2DBC connection factory bean for PostgreSQL DB 
     * 
     * @return ConnectionFactory
     */
    @Bean
    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .database(dbName)
            .username(dbUser)
            .password(dbPassword)
            .port(5432)
            .schema("public")
            .build()
        );
    }


    
    /**
     * Creates a reactive JOOQ DSL Context using the R2DBC connection factory 
     * 
     * @return DSLContext
     */
    @Bean
    @Primary
    public DSLContext dslContext() {
        return DSL.using(connectionFactory());
    }

}
