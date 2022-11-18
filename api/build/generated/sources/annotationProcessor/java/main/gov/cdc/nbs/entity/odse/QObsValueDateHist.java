package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueDateHist is a Querydsl query type for ObsValueDateHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueDateHist extends EntityPathBase<ObsValueDateHist> {

    private static final long serialVersionUID = -1589525544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueDateHist obsValueDateHist = new QObsValueDateHist("obsValueDateHist");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QObsValueDateHistId id;

    public final QObsValueDate obsValueDate;

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public QObsValueDateHist(String variable) {
        this(ObsValueDateHist.class, forVariable(variable), INITS);
    }

    public QObsValueDateHist(Path<? extends ObsValueDateHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueDateHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueDateHist(PathMetadata metadata, PathInits inits) {
        this(ObsValueDateHist.class, metadata, inits);
    }

    public QObsValueDateHist(Class<? extends ObsValueDateHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueDateHistId(forProperty("id")) : null;
        this.obsValueDate = inits.isInitialized("obsValueDate") ? new QObsValueDate(forProperty("obsValueDate"), inits.get("obsValueDate")) : null;
    }

}

