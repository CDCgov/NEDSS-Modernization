package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreatmentProcedure is a Querydsl query type for TreatmentProcedure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentProcedure extends EntityPathBase<TreatmentProcedure> {

    private static final long serialVersionUID = -1761779726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreatmentProcedure treatmentProcedure = new QTreatmentProcedure("treatmentProcedure");

    public final StringPath approachSiteCd = createString("approachSiteCd");

    public final StringPath approachSiteDescTxt = createString("approachSiteDescTxt");

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath cdVersion = createString("cdVersion");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QTreatmentProcedureId id;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QTreatment treatmentUid;

    public QTreatmentProcedure(String variable) {
        this(TreatmentProcedure.class, forVariable(variable), INITS);
    }

    public QTreatmentProcedure(Path<? extends TreatmentProcedure> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreatmentProcedure(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTreatmentProcedure(PathMetadata metadata, PathInits inits) {
        this(TreatmentProcedure.class, metadata, inits);
    }

    public QTreatmentProcedure(Class<? extends TreatmentProcedure> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTreatmentProcedureId(forProperty("id")) : null;
        this.treatmentUid = inits.isInitialized("treatmentUid") ? new QTreatment(forProperty("treatmentUid"), inits.get("treatmentUid")) : null;
    }

}

