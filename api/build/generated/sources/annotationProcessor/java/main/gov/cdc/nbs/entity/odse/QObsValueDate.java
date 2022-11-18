package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueDate is a Querydsl query type for ObsValueDate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueDate extends EntityPathBase<ObsValueDate> {

    private static final long serialVersionUID = 1751853974L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueDate obsValueDate = new QObsValueDate("obsValueDate");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QObsValueDateId id;

    public final QObservation observationUid;

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public QObsValueDate(String variable) {
        this(ObsValueDate.class, forVariable(variable), INITS);
    }

    public QObsValueDate(Path<? extends ObsValueDate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueDate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueDate(PathMetadata metadata, PathInits inits) {
        this(ObsValueDate.class, metadata, inits);
    }

    public QObsValueDate(Class<? extends ObsValueDate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueDateId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

