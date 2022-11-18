package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityIdHistId is a Querydsl query type for EntityIdHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEntityIdHistId extends BeanPath<EntityIdHistId> {

    private static final long serialVersionUID = 185268242L;

    public static final QEntityIdHistId entityIdHistId = new QEntityIdHistId("entityIdHistId");

    public final NumberPath<Short> entityIdSeq = createNumber("entityIdSeq", Short.class);

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QEntityIdHistId(String variable) {
        super(EntityIdHistId.class, forVariable(variable));
    }

    public QEntityIdHistId(Path<? extends EntityIdHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityIdHistId(PathMetadata metadata) {
        super(EntityIdHistId.class, metadata);
    }

}

