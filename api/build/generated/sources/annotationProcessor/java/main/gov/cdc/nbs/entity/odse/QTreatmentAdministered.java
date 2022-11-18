package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTreatmentAdministered is a Querydsl query type for TreatmentAdministered
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentAdministered extends EntityPathBase<TreatmentAdministered> {

    private static final long serialVersionUID = 2092660200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTreatmentAdministered treatmentAdministered = new QTreatmentAdministered("treatmentAdministered");

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

    public final QTreatmentAdministeredId id;

    public final StringPath intervalCd = createString("intervalCd");

    public final StringPath intervalDescTxt = createString("intervalDescTxt");

    public final StringPath rateQty = createString("rateQty");

    public final StringPath rateQtyUnitCd = createString("rateQtyUnitCd");

    public final StringPath routeCd = createString("routeCd");

    public final StringPath routeDescTxt = createString("routeDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final QTreatment treatmentUid;

    public QTreatmentAdministered(String variable) {
        this(TreatmentAdministered.class, forVariable(variable), INITS);
    }

    public QTreatmentAdministered(Path<? extends TreatmentAdministered> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTreatmentAdministered(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTreatmentAdministered(PathMetadata metadata, PathInits inits) {
        this(TreatmentAdministered.class, metadata, inits);
    }

    public QTreatmentAdministered(Class<? extends TreatmentAdministered> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTreatmentAdministeredId(forProperty("id")) : null;
        this.treatmentUid = inits.isInitialized("treatmentUid") ? new QTreatment(forProperty("treatmentUid"), inits.get("treatmentUid")) : null;
    }

}

