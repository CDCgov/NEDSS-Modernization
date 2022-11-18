package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabTest is a Querydsl query type for LabTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabTest extends EntityPathBase<LabTest> {

    private static final long serialVersionUID = -172525935L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabTest labTest = new QLabTest("labTest");

    public final QConditionCode defaultConditionCd;

    public final QProgramAreaCode defaultProgAreaCd;

    public final ComparablePath<Character> drugTestInd = createComparable("drugTestInd", Character.class);

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QLabTestId id;

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final QLabCodingSystem laboratory;

    public final StringPath labTestDescTxt = createString("labTestDescTxt");

    public final SetPath<LabtestLoinc, QLabtestLoinc> labtestLoincs = this.<LabtestLoinc, QLabtestLoinc>createSet("labtestLoincs", LabtestLoinc.class, QLabtestLoinc.class, PathInits.DIRECT2);

    public final QLabtestProgareaMapping labtestProgareaMapping;

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public final ComparablePath<Character> organismResultTestInd = createComparable("organismResultTestInd", Character.class);

    public final ComparablePath<Character> paDerivationExcludeCd = createComparable("paDerivationExcludeCd", Character.class);

    public final StringPath testTypeCd = createString("testTypeCd");

    public QLabTest(String variable) {
        this(LabTest.class, forVariable(variable), INITS);
    }

    public QLabTest(Path<? extends LabTest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabTest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabTest(PathMetadata metadata, PathInits inits) {
        this(LabTest.class, metadata, inits);
    }

    public QLabTest(Class<? extends LabTest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.defaultConditionCd = inits.isInitialized("defaultConditionCd") ? new QConditionCode(forProperty("defaultConditionCd"), inits.get("defaultConditionCd")) : null;
        this.defaultProgAreaCd = inits.isInitialized("defaultProgAreaCd") ? new QProgramAreaCode(forProperty("defaultProgAreaCd")) : null;
        this.id = inits.isInitialized("id") ? new QLabTestId(forProperty("id")) : null;
        this.laboratory = inits.isInitialized("laboratory") ? new QLabCodingSystem(forProperty("laboratory")) : null;
        this.labtestProgareaMapping = inits.isInitialized("labtestProgareaMapping") ? new QLabtestProgareaMapping(forProperty("labtestProgareaMapping"), inits.get("labtestProgareaMapping")) : null;
    }

}

