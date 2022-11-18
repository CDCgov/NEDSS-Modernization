package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIntervention is a Querydsl query type for Intervention
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntervention extends EntityPathBase<Intervention> {

    private static final long serialVersionUID = -1249959218L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIntervention intervention = new QIntervention("intervention");

    public final QAct act;

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Short> ageAtVacc = createNumber("ageAtVacc", Short.class);

    public final StringPath ageAtVaccUnitCd = createString("ageAtVaccUnitCd");

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath classCd = createString("classCd");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> electronicInd = createComparable("electronicInd", Character.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath materialCd = createString("materialCd");

    public final DateTimePath<java.time.Instant> materialExpirationTime = createDateTime("materialExpirationTime", java.time.Instant.class);

    public final StringPath materialLotNm = createString("materialLotNm");

    public final StringPath methodCd = createString("methodCd");

    public final StringPath methodDescTxt = createString("methodDescTxt");

    public final StringPath priorityCd = createString("priorityCd");

    public final StringPath priorityDescTxt = createString("priorityDescTxt");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath qtyAmt = createString("qtyAmt");

    public final StringPath qtyUnitCd = createString("qtyUnitCd");

    public final StringPath reasonCd = createString("reasonCd");

    public final StringPath reasonDescTxt = createString("reasonDescTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath targetSiteCd = createString("targetSiteCd");

    public final StringPath targetSiteDescTxt = createString("targetSiteDescTxt");

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> vaccDoseNbr = createNumber("vaccDoseNbr", Short.class);

    public final StringPath vaccInfoSourceCd = createString("vaccInfoSourceCd");

    public final StringPath vaccMfgrCd = createString("vaccMfgrCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QIntervention(String variable) {
        this(Intervention.class, forVariable(variable), INITS);
    }

    public QIntervention(Path<? extends Intervention> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIntervention(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIntervention(PathMetadata metadata, PathInits inits) {
        this(Intervention.class, metadata, inits);
    }

    public QIntervention(Class<? extends Intervention> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.act = inits.isInitialized("act") ? new QAct(forProperty("act")) : null;
    }

}

