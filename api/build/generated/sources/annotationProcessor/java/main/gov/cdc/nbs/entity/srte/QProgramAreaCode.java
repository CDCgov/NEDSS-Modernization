package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgramAreaCode is a Querydsl query type for ProgramAreaCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgramAreaCode extends EntityPathBase<ProgramAreaCode> {

    private static final long serialVersionUID = -541434992L;

    public static final QProgramAreaCode programAreaCode = new QProgramAreaCode("programAreaCode");

    public final NumberPath<Short> codeSeq = createNumber("codeSeq", Short.class);

    public final StringPath codeSetNm = createString("codeSetNm");

    public final SetPath<ConditionCode, QConditionCode> conditionCodes = this.<ConditionCode, QConditionCode>createSet("conditionCodes", ConditionCode.class, QConditionCode.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final SetPath<LabResult, QLabResult> labResults = this.<LabResult, QLabResult>createSet("labResults", LabResult.class, QLabResult.class, PathInits.DIRECT2);

    public final SetPath<LabTest, QLabTest> labTests = this.<LabTest, QLabTest>createSet("labTests", LabTest.class, QLabTest.class, PathInits.DIRECT2);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final StringPath progAreaDescTxt = createString("progAreaDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QProgramAreaCode(String variable) {
        super(ProgramAreaCode.class, forVariable(variable));
    }

    public QProgramAreaCode(Path<? extends ProgramAreaCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgramAreaCode(PathMetadata metadata) {
        super(ProgramAreaCode.class, metadata);
    }

}

