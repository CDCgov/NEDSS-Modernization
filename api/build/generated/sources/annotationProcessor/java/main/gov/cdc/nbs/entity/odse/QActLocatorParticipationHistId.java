package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActLocatorParticipationHistId is a Querydsl query type for ActLocatorParticipationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActLocatorParticipationHistId extends BeanPath<ActLocatorParticipationHistId> {

    private static final long serialVersionUID = 1305632547L;

    public static final QActLocatorParticipationHistId actLocatorParticipationHistId = new QActLocatorParticipationHistId("actLocatorParticipationHistId");

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final NumberPath<Long> locatorUid = createNumber("locatorUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QActLocatorParticipationHistId(String variable) {
        super(ActLocatorParticipationHistId.class, forVariable(variable));
    }

    public QActLocatorParticipationHistId(Path<? extends ActLocatorParticipationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActLocatorParticipationHistId(PathMetadata metadata) {
        super(ActLocatorParticipationHistId.class, metadata);
    }

}

