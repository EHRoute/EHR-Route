package ehroute.identityservice.repositories;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import ehroute.identityservice.entities.app.BaseEntity;
import ehroute.identityservice.models.resource.ResourceQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Transactional
@SuppressWarnings("unchecked")
public abstract class DefaultResourceRepository<R extends BaseEntity, Y extends UpdatableRecordImpl<Y>, T extends TableImpl<Y>>
implements ResourceRepository<R, Y, T> {


    @Autowired private DSLContext db;
    private Class<R> entityType;

    public DefaultResourceRepository() {
        initializeEntityType();
    }


    public Mono<R> insert(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        return Mono.from(

            // Insert entity and return its id field (first field)
            db
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

        return Mono.from(
        
            // Select by id
            db.selectFrom(entityTable).where(getIdField(entityTable).like(Long.toString(id))).limit(1)
            
        ).flatMap(r -> {

            // Map into the specified type
            return Mono.just(r.into(entityType));

        });

    }


    public Mono<R> findFirstByField(TableField<Y, ?> field, String fieldValue) {

        return Mono.from(
        
            // Select by field and value
            db.selectFrom(field.getTable()).where(field.like(fieldValue)).limit(1)

        ).flatMap(r -> {

            // Map into the specified type
            return Mono.just(r.into(entityType));

        });

    }


    public Flux<R> findAllByField(TableField<Y, ?> field, String fieldValue) {

        return Flux.from(
        
            // Select by field
            db.selectFrom(field.getTable()).where(field.like(fieldValue))

        ).map(r -> r.into(entityType)); // Map into specified type

    }


    public Flux<R> findAllByQuery(TableImpl<Y> entityTable, ResourceQuery query) {

        var hasSortFields = query.getSortFields().size() > 0;
        var usesSeek = (!StringUtils.isEmpty(Long.toString(query.getPage().getSeekId())) && !hasSortFields);
        var usesOffset = !StringUtils.isEmpty(Integer.toString(query.getPage().getOffset()));

        // Construct initial query
        var resourceQuery = db
        .selectFrom(entityTable)
        .where(query.getFilters());

        // Order by custom sort fields if specified
        if (hasSortFields) resourceQuery.orderBy(query.getSortFields());

        // Using seek for pagination (requires Id ordering, and no custom sort fields)
        if (usesSeek) {

            resourceQuery
            .orderBy(getIdField(entityTable).asc())
            .seek(query.getPage().getSeekId())
            .limit(query.getPage().getLimit());

        } 
        // Using offset for pagination
        else if (usesOffset) {

            resourceQuery
            .offset(query.getPage().getOffset())
            .limit(query.getPage().getLimit());

        }

        return Flux.from(resourceQuery).map(r -> r.into(entityType));

    }


    public Mono<R> update(R entity, TableImpl<Y> entityTable) {

        var record = new UpdatableRecordImpl<>(entityTable);
        record.from(entity);

        return Mono.from(

            // Update entity
            db
            .update(entityTable)
            .set(record)

        ).flatMap(id -> {

            // Return updated entity
            return Mono.just(entity);

        });

    }


    public Mono<Integer> deleteById(Long id, TableImpl<Y> entityTable) {
        return Mono.from(
            db.deleteFrom(entityTable).where(getIdField(entityTable).like(Long.toString(id)))
        );
    }


    //#region Helpers 


    private void initializeEntityType() {
        Type genericEntityType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (genericEntityType instanceof ParameterizedType) entityType = (Class<R>) ((ParameterizedType) genericEntityType).getRawType();
        else entityType = (Class<R>) genericEntityType;
    }


    private Field<Long> getIdField(TableImpl<Y> entityTable) {

        // Get the Id field of the entity
        var idField = Arrays.asList(entityTable.fields("id")).stream()
        .filter(f -> f.getName().equals("id"))
        .findFirst();

        // Verify Id field
        if (!idField.isPresent()) throw new NotFoundException("Table has no Id field");

        return (Field<Long>) idField.get();

    }


    //#endregion
}
