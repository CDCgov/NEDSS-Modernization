package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCaseManagement is a Querydsl query type for CaseManagement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCaseManagement extends EntityPathBase<CaseManagement> {

    private static final long serialVersionUID = 1263318474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCaseManagement caseManagement = new QCaseManagement("caseManagement");

    public final StringPath actRefTypeCd = createString("actRefTypeCd");

    public final DateTimePath<java.time.Instant> caseClosedDate = createDateTime("caseClosedDate", java.time.Instant.class);

    public final StringPath caseReviewStatus = createString("caseReviewStatus");

    public final DateTimePath<java.time.Instant> caseReviewStatusDate = createDateTime("caseReviewStatusDate", java.time.Instant.class);

    public final StringPath eharsId = createString("eharsId");

    public final StringPath epiLinkId = createString("epiLinkId");

    public final StringPath fieldFollUpOojOutcome = createString("fieldFollUpOojOutcome");

    public final StringPath fieldRecordNumber = createString("fieldRecordNumber");

    public final StringPath fldFollUpDispo = createString("fldFollUpDispo");

    public final DateTimePath<java.time.Instant> fldFollUpDispoDate = createDateTime("fldFollUpDispoDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> fldFollUpExamDate = createDateTime("fldFollUpExamDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> fldFollUpExpectedDate = createDateTime("fldFollUpExpectedDate", java.time.Instant.class);

    public final StringPath fldFollUpExpectedIn = createString("fldFollUpExpectedIn");

    public final StringPath fldFollUpInternetOutcome = createString("fldFollUpInternetOutcome");

    public final StringPath fldFollUpNotificationPlan = createString("fldFollUpNotificationPlan");

    public final StringPath fldFollUpProvDiagnosis = createString("fldFollUpProvDiagnosis");

    public final StringPath fldFollUpProvExmReason = createString("fldFollUpProvExmReason");

    public final DateTimePath<java.time.Instant> follUpAssignedDate = createDateTime("follUpAssignedDate", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath initFollUp = createString("initFollUp");

    public final DateTimePath<java.time.Instant> initFollUpAssignedDate = createDateTime("initFollUpAssignedDate", java.time.Instant.class);

    public final StringPath initFollUpClinicCode = createString("initFollUpClinicCode");

    public final DateTimePath<java.time.Instant> initFollUpClosedDate = createDateTime("initFollUpClosedDate", java.time.Instant.class);

    public final StringPath initFollUpNotifiable = createString("initFollUpNotifiable");

    public final StringPath initiatingAgncy = createString("initiatingAgncy");

    public final DateTimePath<java.time.Instant> initInterviewAssignedDate = createDateTime("initInterviewAssignedDate", java.time.Instant.class);

    public final StringPath internetFollUp = createString("internetFollUp");

    public final DateTimePath<java.time.Instant> interviewAssignedDate = createDateTime("interviewAssignedDate", java.time.Instant.class);

    public final StringPath oojAgency = createString("oojAgency");

    public final DateTimePath<java.time.Instant> oojDueDate = createDateTime("oojDueDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> oojInitgAgncyOutcDueDate = createDateTime("oojInitgAgncyOutcDueDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> oojInitgAgncyOutcSntDate = createDateTime("oojInitgAgncyOutcSntDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> oojInitgAgncyRecdDate = createDateTime("oojInitgAgncyRecdDate", java.time.Instant.class);

    public final StringPath oojNumber = createString("oojNumber");

    public final StringPath patIntvStatusCd = createString("patIntvStatusCd");

    public final QPublicHealthCase publicHealthCaseUid;

    public final StringPath status900 = createString("status900");

    public final StringPath subjComplexion = createString("subjComplexion");

    public final StringPath subjHair = createString("subjHair");

    public final StringPath subjHeight = createString("subjHeight");

    public final StringPath subjOthIdntfyngInfo = createString("subjOthIdntfyngInfo");

    public final StringPath subjSizeBuild = createString("subjSizeBuild");

    public final DateTimePath<java.time.Instant> survAssignedDate = createDateTime("survAssignedDate", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> survClosedDate = createDateTime("survClosedDate", java.time.Instant.class);

    public final StringPath survPatientFollUp = createString("survPatientFollUp");

    public final StringPath survProvDiagnosis = createString("survProvDiagnosis");

    public final StringPath survProvExmReason = createString("survProvExmReason");

    public final StringPath survProviderContact = createString("survProviderContact");

    public QCaseManagement(String variable) {
        this(CaseManagement.class, forVariable(variable), INITS);
    }

    public QCaseManagement(Path<? extends CaseManagement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCaseManagement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCaseManagement(PathMetadata metadata, PathInits inits) {
        this(CaseManagement.class, metadata, inits);
    }

    public QCaseManagement(Class<? extends CaseManagement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.publicHealthCaseUid = inits.isInitialized("publicHealthCaseUid") ? new QPublicHealthCase(forProperty("publicHealthCaseUid"), inits.get("publicHealthCaseUid")) : null;
    }

}

