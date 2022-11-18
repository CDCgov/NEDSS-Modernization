package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityGroupHistId is a Querydsl query type for EntityGroupHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEntityGroupHistId extends BeanPath<EntityGroupHistId> {

    private static final long serialVersionUID = -793537086L;

    public static final QEntityGroupHistId entityGroupHistId = new QEntityGroupHistId("entityGroupHistId");

    public final NumberPath<Long> entityGroupUid = createNumber("entityGroupUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QEntityGroupHistId(String variable) {
        super(EntityGroupHistId.class, forVariable(variable));
    }

    public QEntityGroupHistId(Path<? extends EntityGroupHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityGroupHistId(PathMetadata metadata) {
        super(EntityGroupHistId.class, metadata);
    }

}

