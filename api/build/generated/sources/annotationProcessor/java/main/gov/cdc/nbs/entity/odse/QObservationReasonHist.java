package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObservationReasonHist is a Querydsl query type for ObservationReasonHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObservationReasonHist extends EntityPathBase<ObservationReasonHist> {

    private static final long serialVersionUID = -410070341L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObservationReasonHist observationReasonHist = new QObservationReasonHist("observationReasonHist");

    public final QObservationReasonHistId id;

    public final StringPath reasonDescTxt = createString("reasonDescTxt");

    public QObservationReasonHist(String variable) {
        this(ObservationReasonHist.class, forVariable(variable), INITS);
    }

    public QObservationReasonHist(Path<? extends ObservationReasonHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObservationReasonHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObservationReasonHist(PathMetadata metadata, PathInits inits) {
        this(ObservationReasonHist.class, metadata, inits);
    }

    public QObservationReasonHist(Class<? extends ObservationReasonHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObservationReasonHistId(forProperty("id")) : null;
    }

}

