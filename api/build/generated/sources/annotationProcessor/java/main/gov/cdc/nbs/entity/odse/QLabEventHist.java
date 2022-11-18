package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabEventHist is a Querydsl query type for LabEventHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabEventHist extends EntityPathBase<LabEventHist> {

    private static final long serialVersionUID = -1290397242L;

    public static final QLabEventHist labEventHist = new QLabEventHist("labEventHist");

    public final StringPath accessionNbr = createString("accessionNbr");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath comparatorCd1 = createString("comparatorCd1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath interpretationCd = createString("interpretationCd");

    public final NumberPath<Long> investigationUid = createNumber("investigationUid", Long.class);

    public final NumberPath<Long> labEventUid = createNumber("labEventUid", Long.class);

    public final StringPath labResultComments = createString("labResultComments");

    public final ComparablePath<Character> labResultStatusCd = createComparable("labResultStatusCd", Character.class);

    public final StringPath labResultTxtVal = createString("labResultTxtVal");

    public final ComparablePath<Character> labRptStatusCd = createComparable("labRptStatusCd", Character.class);

    public final NumberPath<Long> notificationUid = createNumber("notificationUid", Long.class);

    public final StringPath numericUnitCd = createString("numericUnitCd");

    public final StringPath numericValue1 = createString("numericValue1");

    public final NumberPath<java.math.BigDecimal> numericValue2 = createNumber("numericValue2", java.math.BigDecimal.class);

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final StringPath orderedLabTestCd = createString("orderedLabTestCd");

    public final NumberPath<Long> organizationUid = createNumber("organizationUid", Long.class);

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final StringPath reasonForTestCd = createString("reasonForTestCd");

    public final StringPath refRangeFrm = createString("refRangeFrm");

    public final StringPath refRangeTo = createString("refRangeTo");

    public final StringPath resultedLabTestCd = createString("resultedLabTestCd");

    public final StringPath resultedLabTestDrugCd = createString("resultedLabTestDrugCd");

    public final StringPath resultedtestCd = createString("resultedtestCd");

    public final DateTimePath<java.time.Instant> resultRptDt = createDateTime("resultRptDt", java.time.Instant.class);

    public final NumberPath<Long> resultUid = createNumber("resultUid", Long.class);

    public final StringPath separatorCd = createString("separatorCd");

    public final DateTimePath<java.time.Instant> specimenAnalyzedDt = createDateTime("specimenAnalyzedDt", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> specimenCollectionDt = createDateTime("specimenCollectionDt", java.time.Instant.class);

    public final StringPath specimenQty = createString("specimenQty");

    public final StringPath specimenQtyUnit = createString("specimenQtyUnit");

    public final StringPath specimenSrcCd = createString("specimenSrcCd");

    public final StringPath specimenSrcDesc = createString("specimenSrcDesc");

    public final StringPath suscepAccessionNbr = createString("suscepAccessionNbr");

    public final StringPath suscepComparatorCd1 = createString("suscepComparatorCd1");

    public final StringPath suscepInterpretationCd = createString("suscepInterpretationCd");

    public final StringPath suscepLabResultComments = createString("suscepLabResultComments");

    public final StringPath suscepLabResultTxtVal = createString("suscepLabResultTxtVal");

    public final ComparablePath<Character> suscepLabRptStatusCd = createComparable("suscepLabRptStatusCd", Character.class);

    public final ComparablePath<Character> suscepLabRsltStatusCd = createComparable("suscepLabRsltStatusCd", Character.class);

    public final StringPath suscepNumericUnitCd = createString("suscepNumericUnitCd");

    public final StringPath suscepNumericValue1 = createString("suscepNumericValue1");

    public final NumberPath<java.math.BigDecimal> suscepNumericValue2 = createNumber("suscepNumericValue2", java.math.BigDecimal.class);

    public final StringPath suscepOrderedLabTestCd = createString("suscepOrderedLabTestCd");

    public final StringPath suscepRefRangeFrm = createString("suscepRefRangeFrm");

    public final StringPath suscepRefRangeTo = createString("suscepRefRangeTo");

    public final StringPath suscepResultedtestCd = createString("suscepResultedtestCd");

    public final DateTimePath<java.time.Instant> suscepResultRptDt = createDateTime("suscepResultRptDt", java.time.Instant.class);

    public final StringPath suscepSeparatorCd = createString("suscepSeparatorCd");

    public final DateTimePath<java.time.Instant> suscepSpecimenCollectionDt = createDateTime("suscepSpecimenCollectionDt", java.time.Instant.class);

    public final StringPath suscepTestMethodCd = createString("suscepTestMethodCd");

    public final NumberPath<Long> susceptibilityUid = createNumber("susceptibilityUid", Long.class);

    public final StringPath targetSiteCd = createString("targetSiteCd");

    public final StringPath testMethodCd = createString("testMethodCd");

    public QLabEventHist(String variable) {
        super(LabEventHist.class, forVariable(variable));
    }

    public QLabEventHist(Path<? extends LabEventHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabEventHist(PathMetadata metadata) {
        super(LabEventHist.class, metadata);
    }

}

