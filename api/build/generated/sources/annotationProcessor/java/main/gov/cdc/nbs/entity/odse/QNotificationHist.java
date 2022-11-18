package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotificationHist is a Querydsl query type for NotificationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotificationHist extends EntityPathBase<NotificationHist> {

    private static final long serialVersionUID = -396697820L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotificationHist notificationHist = new QNotificationHist("notificationHist");

    public final StringPath activityDurationAmt = createString("activityDurationAmt");

    public final StringPath activityDurationUnitCd = createString("activityDurationUnitCd");

    public final DateTimePath<java.time.Instant> activityFromTime = createDateTime("activityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> activityToTime = createDateTime("activityToTime", java.time.Instant.class);

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final ComparablePath<Character> autoResendInd = createComparable("autoResendInd", Character.class);

    public final StringPath caseClassCd = createString("caseClassCd");

    public final StringPath caseConditionCd = createString("caseConditionCd");

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath confidentialityCd = createString("confidentialityCd");

    public final StringPath confidentialityDescTxt = createString("confidentialityDescTxt");

    public final StringPath confirmationMethodCd = createString("confirmationMethodCd");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final NumberPath<Long> exportReceivingFacilityUid = createNumber("exportReceivingFacilityUid", Long.class);

    public final QNotificationHistId id;

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath messageTxt = createString("messageTxt");

    public final StringPath methodCd = createString("methodCd");

    public final StringPath methodDescTxt = createString("methodDescTxt");

    public final StringPath mmwrWeek = createString("mmwrWeek");

    public final StringPath mmwrYear = createString("mmwrYear");

    public final NumberPath<Long> nbsInterfaceUid = createNumber("nbsInterfaceUid", Long.class);

    public final StringPath nedssVersionNbr = createString("nedssVersionNbr");

    public final QNotification notificationUid;

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath reasonCd = createString("reasonCd");

    public final StringPath reasonDescTxt = createString("reasonDescTxt");

    public final StringPath recordCount = createString("recordCount");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> repeatNbr = createNumber("repeatNbr", Short.class);

    public final DateTimePath<java.time.Instant> rptSentTime = createDateTime("rptSentTime", java.time.Instant.class);

    public final StringPath rptSourceCd = createString("rptSourceCd");

    public final StringPath rptSourceTypeCd = createString("rptSourceTypeCd");

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath txt = createString("txt");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QNotificationHist(String variable) {
        this(NotificationHist.class, forVariable(variable), INITS);
    }

    public QNotificationHist(Path<? extends NotificationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotificationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotificationHist(PathMetadata metadata, PathInits inits) {
        this(NotificationHist.class, metadata, inits);
    }

    public QNotificationHist(Class<? extends NotificationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QNotificationHistId(forProperty("id")) : null;
        this.notificationUid = inits.isInitialized("notificationUid") ? new QNotification(forProperty("notificationUid"), inits.get("notificationUid")) : null;
    }

}

