package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObservationInterpHist is a Querydsl query type for ObservationInterpHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObservationInterpHist extends EntityPathBase<ObservationInterpHist> {

    private static final long serialVersionUID = 1572573067L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObservationInterpHist observationInterpHist = new QObservationInterpHist("observationInterpHist");

    public final QObservationInterpHistId id;

    public QObservationInterpHist(String variable) {
        this(ObservationInterpHist.class, forVariable(variable), INITS);
    }

    public QObservationInterpHist(Path<? extends ObservationInterpHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObservationInterpHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObservationInterpHist(PathMetadata metadata, PathInits inits) {
        this(ObservationInterpHist.class, metadata, inits);
    }

    public QObservationInterpHist(Class<? extends ObservationInterpHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObservationInterpHistId(forProperty("id")) : null;
    }

}

