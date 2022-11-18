package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreatmentAdministeredHist is a Querydsl query type for TreatmentAdministeredHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentAdministeredHist extends EntityPathBase<TreatmentAdministeredHist> {

    private static final long serialVersionUID = 618698026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreatmentAdministeredHist treatmentAdministeredHist = new QTreatmentAdministeredHist("treatmentAdministeredHist");

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath cdVersion = createString("cdVersion");

    public final StringPath doseQty = createString("doseQty");

    public final StringPath doseQtyUnitCd = createString("doseQtyUnitCd");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath formCd = createString("formCd");

    public final StringPath formDescTxt = createString("formDescTxt");

    public final QTreatmentAdministeredHistId id;

    public final StringPath intervalCd = createString("intervalCd");

    public final StringPath intervalDescTxt = createString("intervalDescTxt");

    public final StringPath rateQty = createString("rateQty");

    public final StringPath rateUnitCd = createString("rateUnitCd");

    public final StringPath routeCd = createString("routeCd");

    public final StringPath routeDescTxt = createString("routeDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QTreatmentAdministered treatmentAdministered;

    public QTreatmentAdministeredHist(String variable) {
        this(TreatmentAdministeredHist.class, forVariable(variable), INITS);
    }

    public QTreatmentAdministeredHist(Path<? extends TreatmentAdministeredHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreatmentAdministeredHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTreatmentAdministeredHist(PathMetadata metadata, PathInits inits) {
        this(TreatmentAdministeredHist.class, metadata, inits);
    }

    public QTreatmentAdministeredHist(Class<? extends TreatmentAdministeredHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTreatmentAdministeredHistId(forProperty("id")) : null;
        this.treatmentAdministered = inits.isInitialized("treatmentAdministered") ? new QTreatmentAdministered(forProperty("treatmentAdministered"), inits.get("treatmentAdministered")) : null;
    }

}

