package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentCode is a Querydsl query type for TreatmentCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTreatmentCode extends EntityPathBase<TreatmentCode> {

    private static final long serialVersionUID = -1393466409L;

    public static final QTreatmentCode treatmentCode = new QTreatmentCode("treatmentCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final StringPath doseQty = createString("doseQty");

    public final StringPath doseQtyUnitCd = createString("doseQtyUnitCd");

    public final StringPath drugCd = createString("drugCd");

    public final StringPath drugDescTxt = createString("drugDescTxt");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final StringPath intervalCd = createString("intervalCd");

    public final StringPath intervalDescTxt = createString("intervalDescTxt");

    public final NumberPath<Short> nbsUid = createNumber("nbsUid", Short.class);

    public final StringPath routeCd = createString("routeCd");

    public final StringPath routeDescTxt = createString("routeDescTxt");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath treatmentDescTxt = createString("treatmentDescTxt");

    public final ComparablePath<Character> treatmentTypeCd = createComparable("treatmentTypeCd", Character.class);

    public QTreatmentCode(String variable) {
        super(TreatmentCode.class, forVariable(variable));
    }

    public QTreatmentCode(Path<? extends TreatmentCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentCode(PathMetadata metadata) {
        super(TreatmentCode.class, metadata);
    }

}

