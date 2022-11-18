package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreatmentProcedureHist is a Querydsl query type for TreatmentProcedureHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentProcedureHist extends EntityPathBase<TreatmentProcedureHist> {

    private static final long serialVersionUID = 413821492L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreatmentProcedureHist treatmentProcedureHist = new QTreatmentProcedureHist("treatmentProcedureHist");

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

    public final QTreatmentProcedureHistId id;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QTreatmentProcedure treatmentProcedure;

    public QTreatmentProcedureHist(String variable) {
        this(TreatmentProcedureHist.class, forVariable(variable), INITS);
    }

    public QTreatmentProcedureHist(Path<? extends TreatmentProcedureHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreatmentProcedureHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTreatmentProcedureHist(PathMetadata metadata, PathInits inits) {
        this(TreatmentProcedureHist.class, metadata, inits);
    }

    public QTreatmentProcedureHist(Class<? extends TreatmentProcedureHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTreatmentProcedureHistId(forProperty("id")) : null;
        this.treatmentProcedure = inits.isInitialized("treatmentProcedure") ? new QTreatmentProcedure(forProperty("treatmentProcedure"), inits.get("treatmentProcedure")) : null;
    }

}

