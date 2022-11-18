package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabtestProgareaMapping is a Querydsl query type for LabtestProgareaMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabtestProgareaMapping extends EntityPathBase<LabtestProgareaMapping> {

    private static final long serialVersionUID = -1962304202L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabtestProgareaMapping labtestProgareaMapping = new QLabtestProgareaMapping("labtestProgareaMapping");

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath conditionDescTxt = createString("conditionDescTxt");

    public final StringPath conditionShortNm = createString("conditionShortNm");

    public final QLabtestProgareaMappingId id;

    public final NumberPath<Short> indentLevelNbr = createNumber("indentLevelNbr", Short.class);

    public final QLabTest labTest;

    public final StringPath labTestDescTxt = createString("labTestDescTxt");

    public final ComparablePath<Character> organismResultTestInd = createComparable("organismResultTestInd", Character.class);

    public final StringPath progAreaCd = createString("progAreaCd");

    public final StringPath progAreaDescTxt = createString("progAreaDescTxt");

    public final StringPath testTypeCd = createString("testTypeCd");

    public QLabtestProgareaMapping(String variable) {
        this(LabtestProgareaMapping.class, forVariable(variable), INITS);
    }

    public QLabtestProgareaMapping(Path<? extends LabtestProgareaMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabtestProgareaMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabtestProgareaMapping(PathMetadata metadata, PathInits inits) {
        this(LabtestProgareaMapping.class, metadata, inits);
    }

    public QLabtestProgareaMapping(Class<? extends LabtestProgareaMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QLabtestProgareaMappingId(forProperty("id")) : null;
        this.labTest = inits.isInitialized("labTest") ? new QLabTest(forProperty("labTest"), inits.get("labTest")) : null;
    }

}

