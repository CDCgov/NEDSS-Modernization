package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConditionCode is a Querydsl query type for ConditionCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConditionCode extends EntityPathBase<ConditionCode> {

    private static final long serialVersionUID = 624971514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConditionCode conditionCode = new QConditionCode("conditionCode");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath coinfectionGrpCd = createString("coinfectionGrpCd");

    public final StringPath conditionCodesetNm = createString("conditionCodesetNm");

    public final StringPath conditionDescTxt = createString("conditionDescTxt");

    public final NumberPath<Short> conditionSeqNum = createNumber("conditionSeqNum", Short.class);

    public final StringPath conditionShortNm = createString("conditionShortNm");

    public final ComparablePath<Character> contactTracingEnableInd = createComparable("contactTracingEnableInd", Character.class);

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath familyCd = createString("familyCd");

    public final StringPath id = createString("id");

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final StringPath investigationFormCd = createString("investigationFormCd");

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final ComparablePath<Character> labReportEnableInd = createComparable("labReportEnableInd", Character.class);

    public final SetPath<LabResult, QLabResult> labResults = this.<LabResult, QLabResult>createSet("labResults", LabResult.class, QLabResult.class, PathInits.DIRECT2);

    public final SetPath<LabTest, QLabTest> labTests = this.<LabTest, QLabTest>createSet("labTests", LabTest.class, QLabTest.class, PathInits.DIRECT2);

    public final SetPath<LdfPageSet, QLdfPageSet> ldfPageSets = this.<LdfPageSet, QLdfPageSet>createSet("ldfPageSets", LdfPageSet.class, QLdfPageSet.class, PathInits.DIRECT2);

    public final SetPath<LoincCondition, QLoincCondition> loincConditions = this.<LoincCondition, QLoincCondition>createSet("loincConditions", LoincCondition.class, QLoincCondition.class, PathInits.DIRECT2);

    public final SetPath<LoincSnomedCondition, QLoincSnomedCondition> loincSnomedConditions = this.<LoincSnomedCondition, QLoincSnomedCondition>createSet("loincSnomedConditions", LoincSnomedCondition.class, QLoincSnomedCondition.class, PathInits.DIRECT2);

    public final ComparablePath<Character> morbReportEnableInd = createComparable("morbReportEnableInd", Character.class);

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public final StringPath nndEntityIdentifier = createString("nndEntityIdentifier");

    public final ComparablePath<Character> nndInd = createComparable("nndInd", Character.class);

    public final StringPath nndSummaryEntityIdentifier = createString("nndSummaryEntityIdentifier");

    public final StringPath parentIsCd = createString("parentIsCd");

    public final ComparablePath<Character> portReqIndCd = createComparable("portReqIndCd", Character.class);

    public final QProgramAreaCode progAreaCd;

    public final ComparablePath<Character> reportableMorbidityInd = createComparable("reportableMorbidityInd", Character.class);

    public final ComparablePath<Character> reportableSummaryInd = createComparable("reportableSummaryInd", Character.class);

    public final StringPath rhapActionValue = createString("rhapActionValue");

    public final StringPath rhapParseNbsInd = createString("rhapParseNbsInd");

    public final SetPath<SnomedCondition, QSnomedCondition> snomedConditions = this.<SnomedCondition, QSnomedCondition>createSet("snomedConditions", SnomedCondition.class, QSnomedCondition.class, PathInits.DIRECT2);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath summaryInvestigationFormCd = createString("summaryInvestigationFormCd");

    public final ComparablePath<Character> treatmentEnableInd = createComparable("treatmentEnableInd", Character.class);

    public final ComparablePath<Character> vaccineEnableInd = createComparable("vaccineEnableInd", Character.class);

    public QConditionCode(String variable) {
        this(ConditionCode.class, forVariable(variable), INITS);
    }

    public QConditionCode(Path<? extends ConditionCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConditionCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConditionCode(PathMetadata metadata, PathInits inits) {
        this(ConditionCode.class, metadata, inits);
    }

    public QConditionCode(Class<? extends ConditionCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.progAreaCd = inits.isInitialized("progAreaCd") ? new QProgramAreaCode(forProperty("progAreaCd")) : null;
    }

}

