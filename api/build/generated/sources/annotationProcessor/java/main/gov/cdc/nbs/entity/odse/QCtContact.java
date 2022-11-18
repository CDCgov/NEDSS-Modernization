package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContact is a Querydsl query type for CtContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContact extends EntityPathBase<CtContact> {

    private static final long serialVersionUID = 291509720L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContact ctContact = new QCtContact("ctContact");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath contactEntityEpiLinkId = createString("contactEntityEpiLinkId");

    public final QPublicHealthCase contactEntityPhcUid;

    public final QNBSEntity contactNBSEntityUid;

    public final StringPath contactReferralBasisCd = createString("contactReferralBasisCd");

    public final StringPath contactStatus = createString("contactStatus");

    public final StringPath dispositionCd = createString("dispositionCd");

    public final DateTimePath<java.time.Instant> dispositionDate = createDateTime("dispositionDate", java.time.Instant.class);

    public final StringPath evaluationCompletedCd = createString("evaluationCompletedCd");

    public final DateTimePath<java.time.Instant> evaluationDate = createDateTime("evaluationDate", java.time.Instant.class);

    public final StringPath evaluationTxt = createString("evaluationTxt");

    public final StringPath groupNameCd = createString("groupNameCd");

    public final StringPath healthStatusCd = createString("healthStatusCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> investigatorAssignedDate = createDateTime("investigatorAssignedDate", java.time.Instant.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final NumberPath<Long> namedDuringInterviewUid = createNumber("namedDuringInterviewUid", Long.class);

    public final DateTimePath<java.time.Instant> namedOnDate = createDateTime("namedOnDate", java.time.Instant.class);

    public final StringPath priorityCd = createString("priorityCd");

    public final StringPath processingDecisionCd = createString("processingDecisionCd");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath relationshipCd = createString("relationshipCd");

    public final StringPath riskFactorCd = createString("riskFactorCd");

    public final StringPath riskFactorTxt = createString("riskFactorTxt");

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final ComparablePath<Character> sharedIndCd = createComparable("sharedIndCd", Character.class);

    public final StringPath subjectEntityEpiLinkId = createString("subjectEntityEpiLinkId");

    public final QPublicHealthCase subjectEntityPhcUid;

    public final QNBSEntity subjectNBSEntityUid;

    public final StringPath symptomCd = createString("symptomCd");

    public final DateTimePath<java.time.Instant> symptomOnsetDate = createDateTime("symptomOnsetDate", java.time.Instant.class);

    public final StringPath symptomTxt = createString("symptomTxt");

    public final QAct thirdPartyEntityPhcUid;

    public final QNBSEntity thirdPartyNBSEntityUid;

    public final StringPath treatmentEndCd = createString("treatmentEndCd");

    public final DateTimePath<java.time.Instant> treatmentEndDate = createDateTime("treatmentEndDate", java.time.Instant.class);

    public final StringPath treatmentInitiatedCd = createString("treatmentInitiatedCd");

    public final StringPath treatmentNotEndRsnCd = createString("treatmentNotEndRsnCd");

    public final StringPath treatmentNotStartRsnCd = createString("treatmentNotStartRsnCd");

    public final DateTimePath<java.time.Instant> treatmentStartDate = createDateTime("treatmentStartDate", java.time.Instant.class);

    public final StringPath treatmentTxt = createString("treatmentTxt");

    public final StringPath txt = createString("txt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCtContact(String variable) {
        this(CtContact.class, forVariable(variable), INITS);
    }

    public QCtContact(Path<? extends CtContact> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContact(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContact(PathMetadata metadata, PathInits inits) {
        this(CtContact.class, metadata, inits);
    }

    public QCtContact(Class<? extends CtContact> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contactEntityPhcUid = inits.isInitialized("contactEntityPhcUid") ? new QPublicHealthCase(forProperty("contactEntityPhcUid"), inits.get("contactEntityPhcUid")) : null;
        this.contactNBSEntityUid = inits.isInitialized("contactNBSEntityUid") ? new QNBSEntity(forProperty("contactNBSEntityUid")) : null;
        this.subjectEntityPhcUid = inits.isInitialized("subjectEntityPhcUid") ? new QPublicHealthCase(forProperty("subjectEntityPhcUid"), inits.get("subjectEntityPhcUid")) : null;
        this.subjectNBSEntityUid = inits.isInitialized("subjectNBSEntityUid") ? new QNBSEntity(forProperty("subjectNBSEntityUid")) : null;
        this.thirdPartyEntityPhcUid = inits.isInitialized("thirdPartyEntityPhcUid") ? new QAct(forProperty("thirdPartyEntityPhcUid")) : null;
        this.thirdPartyNBSEntityUid = inits.isInitialized("thirdPartyNBSEntityUid") ? new QNBSEntity(forProperty("thirdPartyNBSEntityUid")) : null;
    }

}

