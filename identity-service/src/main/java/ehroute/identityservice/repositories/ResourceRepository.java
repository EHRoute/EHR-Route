package ehroute.identityservice.repositories;

import org.jooq.TableField;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;

import ehroute.identityservice.entities.app.BaseEntity;
import ehroute.identityservice.models.resource.ResourceQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceRepository<R extends BaseEntity, Y extends UpdatableRecordImpl<Y>, T extends TableImpl<Y>> {
    public Mono<R> insert(R entity, TableImpl<Y> entityTable);
    public Mono<R> findById(Long id, TableImpl<Y> entityTable);
    public Mono<R> findFirstByField(TableField<Y, ?> field, String fieldValue);
    public Flux<R> findAllByField(TableField<Y, ?> field, String fieldValue);
    public Flux<R> findAllByQuery(TableImpl<Y> entityTable, ResourceQuery query);
    public Mono<R> update(R entity, TableImpl<Y> entityTable);
    public Mono<Integer> deleteById(Long id, TableImpl<Y> entityTable);
}
