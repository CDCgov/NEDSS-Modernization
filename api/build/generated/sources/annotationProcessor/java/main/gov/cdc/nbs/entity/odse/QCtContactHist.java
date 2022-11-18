package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContactHist is a Querydsl query type for CtContactHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContactHist extends EntityPathBase<CtContactHist> {

    private static final long serialVersionUID = -1789674214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContactHist ctContactHist = new QCtContactHist("ctContactHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath contactEntityEpiLinkId = createString("contactEntityEpiLinkId");

    public final NumberPath<Long> contactEntityPhcUid = createNumber("contactEntityPhcUid", Long.class);

    public final NumberPath<Long> contactEntityUid = createNumber("contactEntityUid", Long.class);

    public final StringPath contactReferralBasisCd = createString("contactReferralBasisCd");

    public final StringPath contactStatus = createString("contactStatus");

    public final QCtContact ctContactUid;

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

    public final ComparablePath<Character> sharedIndCd = createComparable("sharedIndCd", Character.class);

    public final StringPath subjectEntityEpiLinkId = createString("subjectEntityEpiLinkId");

    public final NumberPath<Long> subjectEntityPhcUid = createNumber("subjectEntityPhcUid", Long.class);

    public final NumberPath<Long> subjectEntityUid = createNumber("subjectEntityUid", Long.class);

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

    public QCtContactHist(String variable) {
        this(CtContactHist.class, forVariable(variable), INITS);
    }

    public QCtContactHist(Path<? extends CtContactHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContactHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContactHist(PathMetadata metadata, PathInits inits) {
        this(CtContactHist.class, metadata, inits);
    }

    public QCtContactHist(Class<? extends CtContactHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ctContactUid = inits.isInitialized("ctContactUid") ? new QCtContact(forProperty("ctContactUid"), inits.get("ctContactUid")) : null;
        this.thirdPartyEntityPhcUid = inits.isInitialized("thirdPartyEntityPhcUid") ? new QAct(forProperty("thirdPartyEntityPhcUid")) : null;
        this.thirdPartyNBSEntityUid = inits.isInitialized("thirdPartyNBSEntityUid") ? new QNBSEntity(forProperty("thirdPartyNBSEntityUid")) : null;
    }

}

