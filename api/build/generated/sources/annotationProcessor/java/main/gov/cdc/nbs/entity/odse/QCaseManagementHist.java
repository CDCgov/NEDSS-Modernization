package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCaseManagementHist is a Querydsl query type for CaseManagementHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCaseManagementHist extends EntityPathBase<CaseManagementHist> {

    private static final long serialVersionUID = -953478132L;

    public static final QCaseManagementHist caseManagementHist = new QCaseManagementHist("caseManagementHist");

    public final StringPath actRefTypeCd = createString("actRefTypeCd");

    public final DateTimePath<java.time.Instant> caseClosedDate = createDateTime("caseClosedDate", java.time.Instant.class);

    public final NumberPath<Long> caseManagementUid = createNumber("caseManagementUid", Long.class);

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

    public final NumberPath<Long> publicHealthCaseUid = createNumber("publicHealthCaseUid", Long.class);

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

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCaseManagementHist(String variable) {
        super(CaseManagementHist.class, forVariable(variable));
    }

    public QCaseManagementHist(Path<? extends CaseManagementHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCaseManagementHist(PathMetadata metadata) {
        super(CaseManagementHist.class, metadata);
    }

}

