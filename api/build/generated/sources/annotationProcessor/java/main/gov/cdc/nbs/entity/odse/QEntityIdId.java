package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityIdId is a Querydsl query type for EntityIdId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEntityIdId extends BeanPath<EntityIdId> {

    private static final long serialVersionUID = 952614864L;

    public static final QEntityIdId entityIdId = new QEntityIdId("entityIdId");

    public final NumberPath<Short> entityIdSeq = createNumber("entityIdSeq", Short.class);

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public QEntityIdId(String variable) {
        super(EntityIdId.class, forVariable(variable));
    }

    public QEntityIdId(Path<? extends EntityIdId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityIdId(PathMetadata metadata) {
        super(EntityIdId.class, metadata);
    }

}

