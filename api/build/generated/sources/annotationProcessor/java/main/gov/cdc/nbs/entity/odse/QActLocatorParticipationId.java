package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActLocatorParticipationId is a Querydsl query type for ActLocatorParticipationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActLocatorParticipationId extends BeanPath<ActLocatorParticipationId> {

    private static final long serialVersionUID = -214476959L;

    public static final QActLocatorParticipationId actLocatorParticipationId = new QActLocatorParticipationId("actLocatorParticipationId");

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final NumberPath<Long> locatorUid = createNumber("locatorUid", Long.class);

    public QActLocatorParticipationId(String variable) {
        super(ActLocatorParticipationId.class, forVariable(variable));
    }

    public QActLocatorParticipationId(Path<? extends ActLocatorParticipationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActLocatorParticipationId(PathMetadata metadata) {
        super(ActLocatorParticipationId.class, metadata);
    }

}

