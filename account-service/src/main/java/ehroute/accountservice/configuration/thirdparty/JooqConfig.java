package ehroute.accountservice.configuration.thirdparty;
import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.*;


@Configuration
public class JooqConfig {

    @Bean
    public DSLContext dslContext(R2dbcEntityTemplate r2dbcEntityTemplate, ConnectionFactory connectionFactory) {
        R2dbcDialect r2dbcDialect = DialectResolver.getDialect(connectionFactory);
        SQLDialect jooqDialect = translateToJooqDialect(r2dbcDialect);
        DSLContext dslContext = DSL.using(jooqDialect);
        dslContext.configuration().data("databaseClient", r2dbcEntityTemplate.getDatabaseClient());
        return dslContext;
    }

    private SQLDialect translateToJooqDialect(R2dbcDialect r2dbcDialect) {
        if (r2dbcDialect instanceof MySqlDialect) return SQLDialect.MYSQL;
        if (r2dbcDialect instanceof H2Dialect) return SQLDialect.H2;
        if (r2dbcDialect instanceof PostgresDialect) return SQLDialect.POSTGRES;
        throw new IllegalArgumentException("Unsupported r2dbc dialect " + r2dbcDialect.getClass());
    }

}
