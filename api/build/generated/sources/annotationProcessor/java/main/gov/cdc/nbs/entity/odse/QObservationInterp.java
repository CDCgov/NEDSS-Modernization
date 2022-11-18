package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObservationInterp is a Querydsl query type for ObservationInterp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObservationInterp extends EntityPathBase<ObservationInterp> {

    private static final long serialVersionUID = 1177340105L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObservationInterp observationInterp = new QObservationInterp("observationInterp");

    public final QObservationInterpId id;

    public final QObservation observationUid;

    public QObservationInterp(String variable) {
        this(ObservationInterp.class, forVariable(variable), INITS);
    }

    public QObservationInterp(Path<? extends ObservationInterp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObservationInterp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObservationInterp(PathMetadata metadata, PathInits inits) {
        this(ObservationInterp.class, metadata, inits);
    }

    public QObservationInterp(Class<? extends ObservationInterp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObservationInterpId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

