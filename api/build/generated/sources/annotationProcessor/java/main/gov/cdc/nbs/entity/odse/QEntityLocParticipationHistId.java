package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityLocParticipationHistId is a Querydsl query type for EntityLocParticipationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEntityLocParticipationHistId extends BeanPath<EntityLocParticipationHistId> {

    private static final long serialVersionUID = 911340248L;

    public static final QEntityLocParticipationHistId entityLocParticipationHistId = new QEntityLocParticipationHistId("entityLocParticipationHistId");

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final NumberPath<Long> locatorUid = createNumber("locatorUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QEntityLocParticipationHistId(String variable) {
        super(EntityLocParticipationHistId.class, forVariable(variable));
    }

    public QEntityLocParticipationHistId(Path<? extends EntityLocParticipationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityLocParticipationHistId(PathMetadata metadata) {
        super(EntityLocParticipationHistId.class, metadata);
    }

}

