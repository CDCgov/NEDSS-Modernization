package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObservationReason is a Querydsl query type for ObservationReason
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObservationReason extends EntityPathBase<ObservationReason> {

    private static final long serialVersionUID = 1426138105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObservationReason observationReason = new QObservationReason("observationReason");

    public final QObservationReasonId id;

    public final QObservation observationUid;

    public final StringPath reasonDescTxt = createString("reasonDescTxt");

    public QObservationReason(String variable) {
        this(ObservationReason.class, forVariable(variable), INITS);
    }

    public QObservationReason(Path<? extends ObservationReason> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObservationReason(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObservationReason(PathMetadata metadata, PathInits inits) {
        this(ObservationReason.class, metadata, inits);
    }

    public QObservationReason(Class<? extends ObservationReason> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObservationReasonId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

