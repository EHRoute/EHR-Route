package ehroute.identityservice.repositories;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.jooq.DSLContext;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import ehroute.identityservice.entities.app.BaseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Transactional
public abstract class DefaultResourceRepository<R extends BaseEntity, Y extends UpdatableRecordImpl<Y>, T extends TableImpl<Y>>
implements ResourceRepository<R, Y, T> {


    @Autowired private DSLContext ctx;
    private Class<R> entityType;

    public DefaultResourceRepository() {
        initializeEntityType();
    }


    public Mono<R> insert(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        return Mono.from(

            // Insert entity and return its id field (first field)
            ctx
            .insertInto(entityTable)
            .set(record)
            .returningResult(entityTable.fields("id"))

        ).flatMap(r -> {

            // Return entity with it's saved/created id
            entity.setId(Long.parseLong(r.get("id").toString()));
            return Mono.just(entity);

        });

    }


    public Mono<R> findById(Long id, TableImpl<Y> entityTable) {

        // Get the id field of the entity
        var idField = Arrays.asList(entityTable.fields("id")).stream()
        .filter(f -> f.getName().equals("id"))
        .findFirst();

        // Verify ID field
        if (!idField.isPresent()) throw new NotFoundException("Table has no id field");

        return Mono.from(
        
            // Select by id
            ctx.selectFrom(entityTable).where(idField.get().like(Long.toString(id))).limit(1)
            
        ).flatMap(r -> {

            // Map into the specified type
            return Mono.just(r.into(entityType));

        });

    }


    public Mono<R> findFirstByField(TableField<Y, ?> field, String fieldValue) {

        return Mono.from(
        
            // Select by field and value
            ctx.selectFrom(field.getTable()).where(field.like(fieldValue)).limit(1)

        ).flatMap(r -> {

            // Map into the specified type
            return Mono.just(r.into(entityType));

        });

    }


    public Flux<R> findAllByField(TableField<Y, ?> field, String fieldValue) {

        return Flux.from(
        
            // Select by field
            ctx.selectFrom(field.getTable()).where(field.like(fieldValue))

        ).map(r -> r.into(entityType)); // Map into specified type

    }


    public Flux<R> findAllWithPagination(TableImpl<Y> entityTable) {

        return null;

    }


    public Mono<R> update(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        return Mono.from(

            // Update entity
            ctx
            .update(entityTable)
            .set(record)

        ).flatMap(id -> {

            // Return updated entity
            return Mono.just(entity);

        });

    }


    public Mono<Integer> deleteById(Long id, TableImpl<Y> entityTable) {

        // Get the id field of the entity
        var idField = Arrays.asList(entityTable.fields("id")).stream()
        .filter(f -> f.getName().equals("id"))
        .findFirst();

        // Verify ID field
        if (!idField.isPresent()) throw new NotFoundException("Table has no id field");

        return Mono.from(

            // Delete by id
            ctx.deleteFrom(entityTable).where(idField.get().like(Long.toString(id)))

        );
    }


    @SuppressWarnings("unchecked")
    private void initializeEntityType() {
        Type genericEntityType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (genericEntityType instanceof ParameterizedType) entityType = (Class<R>) ((ParameterizedType) genericEntityType).getRawType();
        else entityType = (Class<R>) genericEntityType;
    }

}
