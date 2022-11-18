package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsConversionMapping is a Querydsl query type for NbsConversionMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConversionMapping extends EntityPathBase<NbsConversionMapping> {

    private static final long serialVersionUID = -1864015792L;

    public static final QNbsConversionMapping nbsConversionMapping = new QNbsConversionMapping("nbsConversionMapping");

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final NumberPath<Integer> blockIdNbr = createNumber("blockIdNbr", Integer.class);

    public final NumberPath<Long> conditionCdGroupId = createNumber("conditionCdGroupId", Long.class);

    public final StringPath conversionType = createString("conversionType");

    public final StringPath fromCode = createString("fromCode");

    public final StringPath fromCodeSetNm = createString("fromCodeSetNm");

    public final StringPath fromDataType = createString("fromDataType");

    public final StringPath fromDbLocation = createString("fromDbLocation");

    public final StringPath fromLabel = createString("fromLabel");

    public final StringPath fromOtherQuestionId = createString("fromOtherQuestionId");

    public final StringPath fromQuestionId = createString("fromQuestionId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath legacyBlockInd = createString("legacyBlockInd");

    public final ComparablePath<Character> otherInd = createComparable("otherInd", Character.class);

    public final StringPath toCode = createString("toCode");

    public final StringPath toCodeSetNm = createString("toCodeSetNm");

    public final StringPath toDataType = createString("toDataType");

    public final StringPath toDbLocation = createString("toDbLocation");

    public final StringPath toLabel = createString("toLabel");

    public final StringPath toQuestionId = createString("toQuestionId");

    public final StringPath translationRequiredInd = createString("translationRequiredInd");

    public final StringPath triggerQuestionId = createString("triggerQuestionId");

    public final StringPath triggerQuestionValue = createString("triggerQuestionValue");

    public final ComparablePath<Character> unitInd = createComparable("unitInd", Character.class);

    public final StringPath unitTypeCd = createString("unitTypeCd");

    public final StringPath unitValue = createString("unitValue");

    public QNbsConversionMapping(String variable) {
        super(NbsConversionMapping.class, forVariable(variable));
    }

    public QNbsConversionMapping(Path<? extends NbsConversionMapping> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsConversionMapping(PathMetadata metadata) {
        super(NbsConversionMapping.class, metadata);
    }

}

