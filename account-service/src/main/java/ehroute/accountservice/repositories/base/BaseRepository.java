package ehroute.accountservice.repositories.base;
import com.fasterxml.jackson.databind.ObjectMapper;
import ehroute.accountservice.entities.user.User;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.jooq.conf.ParamType;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;


@Component
@Transactional
public class BaseRepository<R, Y extends UpdatableRecordImpl<Y>, T extends TableImpl<Y>> {

    // <editor-fold desc="Dependencies">

    @Autowired private DSLContext ctx;
    @Autowired private DatabaseClient db;
    @Autowired private R2dbcEntityTemplate template;
    @Autowired private ObjectMapper mapper;

    // </editor-fold>


    // <editor-fold desc="Methods">

    public Mono<R> insert(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        var insertQry = ctx.insertInto(entityTable).set(record).getSQL(ParamType.INLINED).replace("\"", "");

        db.sql(insertQry).then().subscribe();

        return Mono.just(entity);

    }


    public Mono<Void> update(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        var updateQry = ctx
                .update(entityTable)
                .set(record)
                .getSQL(ParamType.INLINED)
                .replace("\"", "");

        return db.sql(updateQry).then();

    }


    public Mono<R> findById(Long id, Class<R> entityClass, TableImpl<Y> entityTable) {

        var selectQry = ctx
                .selectFrom(entityTable)
                .where(entityTable.field("EMAIL", String.class).eq("string@gmail.com"))
                .getSQL(ParamType.INLINED)
                .replace("\"", "");

        return db.sql(selectQry).fetch().first().map(e -> mapper.convertValue(e, entityClass));

    }


    public Flux<Map<String, Object>> findAllByField(String field, String value, Class<R> entityClass, TableImpl<Y> entityTable) {

        var selectQry = ctx
                .selectFrom(entityTable)
                .where(entityTable.field(field, String.class).eq(value))
                .getSQL(ParamType.INLINED)
                .replace("\"", "");

        return db.sql(selectQry).fetch().all();

    }


    public Flux<User> findAll() {
        return template.select(User.class).all();
    }


    // TODO Implement group fetching when needed
    public List<Mono<R>> findAll(R entity, UpdatableRecord<Y> entityRecord, TableImpl<Y> entityTable) {

        /*var selectQry = ctx
                .selectFrom(entityTable)
                .where(Arrays.stream(entityTable.fields()).anyMatch(entityRecord.fields()))*/

        return null;

    }


    public Mono<Void> deleteById(Long id, TableImpl<Y> entityTable) {

        var deleteQry = ctx
                .delete(entityTable)
                .where(entityTable.field("ID", Long.class).eq(id))
                .getSQL(ParamType.INLINED)
                .replace("\"", "");

        return db.sql(deleteQry).then();

    }

}
