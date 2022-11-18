package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityLocatorParticipationId is a Querydsl query type for EntityLocatorParticipationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEntityLocatorParticipationId extends BeanPath<EntityLocatorParticipationId> {

    private static final long serialVersionUID = 689328256L;

    public static final QEntityLocatorParticipationId entityLocatorParticipationId = new QEntityLocatorParticipationId("entityLocatorParticipationId");

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final NumberPath<Long> locatorUid = createNumber("locatorUid", Long.class);

    public QEntityLocatorParticipationId(String variable) {
        super(EntityLocatorParticipationId.class, forVariable(variable));
    }

    public QEntityLocatorParticipationId(Path<? extends EntityLocatorParticipationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityLocatorParticipationId(PathMetadata metadata) {
        super(EntityLocatorParticipationId.class, metadata);
    }

}

