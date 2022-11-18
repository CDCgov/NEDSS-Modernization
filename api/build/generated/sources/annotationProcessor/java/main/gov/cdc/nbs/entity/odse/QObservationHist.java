package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObservationHist is a Querydsl query type for ObservationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObservationHist extends EntityPathBase<ObservationHist> {

    private static final long serialVersionUID = 1855937047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObservationHist observationHist = new QObservationHist("observationHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath altCd = createString("altCd");

    public final StringPath altCdDescTxt = createString("altCdDescTxt");

    public final StringPath altCdSystemCd = createString("altCdSystemCd");

    public final StringPath altCdSystemDescTxt = createString("altCdSystemDescTxt");

    public final StringPath cd = createString("cd");

    public final ComparablePath<Character> cdDerivedInd = createComparable("cdDerivedInd", Character.class);

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cdSystemCd = createString("cdSystemCd");

    public final StringPath cdSystemDescTxt = createString("cdSystemDescTxt");

    public final StringPath cdVersion = createString("cdVersion");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath ctrlCdDisplayForm = createString("ctrlCdDisplayForm");

    public final StringPath ctrlCdUserDefined1 = createString("ctrlCdUserDefined1");

    public final StringPath ctrlCdUserDefined2 = createString("ctrlCdUserDefined2");

    public final StringPath ctrlCdUserDefined3 = createString("ctrlCdUserDefined3");

    public final StringPath ctrlCdUserDefined4 = createString("ctrlCdUserDefined4");

    public final NumberPath<Short> derivationExp = createNumber("derivationExp", Short.class);

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> electronicInd = createComparable("electronicInd", Character.class);

    public final StringPath groupLevelCd = createString("groupLevelCd");

    public final QObservationHistId id;

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath labConditionCd = createString("labConditionCd");

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath obsDomainCd = createString("obsDomainCd");

    public final StringPath obsDomainCdSt1 = createString("obsDomainCdSt1");

    public final QObservation observationUid;

    public final ComparablePath<Character> pnuCd = createComparable("pnuCd", Character.class);

    public final StringPath pregnantIndCd = createString("pregnantIndCd");

    public final NumberPath<Short> pregnantWeek = createNumber("pregnantWeek", Short.class);

    public final StringPath priorityCd = createString("priorityCd");

    public final StringPath priorityDescTxt = createString("priorityDescTxt");

    public final StringPath processingDecisionCd = createString("processingDecisionCd");

    public final StringPath processingDecisionTxt = createString("processingDecisionTxt");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final DateTimePath<java.time.Instant> rptToStateTime = createDateTime("rptToStateTime", java.time.Instant.class);

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final NumberPath<Long> subjectPersonUid = createNumber("subjectPersonUid", Long.class);

    public final StringPath targetSiteCd = createString("targetSiteCd");

    public final StringPath targetSiteDescTxt = createString("targetSiteDescTxt");

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath valueCd = createString("valueCd");

    public final ComparablePath<Character> ynuCd = createComparable("ynuCd", Character.class);

    public QObservationHist(String variable) {
        this(ObservationHist.class, forVariable(variable), INITS);
    }

    public QObservationHist(Path<? extends ObservationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObservationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObservationHist(PathMetadata metadata, PathInits inits) {
        this(ObservationHist.class, metadata, inits);
    }

    public QObservationHist(Class<? extends ObservationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObservationHistId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

