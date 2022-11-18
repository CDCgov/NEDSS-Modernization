package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLookupQuestion is a Querydsl query type for LookupQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLookupQuestion extends EntityPathBase<LookupQuestion> {

    private static final long serialVersionUID = -600944201L;

    public static final QLookupQuestion lookupQuestion = new QLookupQuestion("lookupQuestion");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath fromCodeSet = createString("fromCodeSet");

    public final StringPath fromCodeSystemCd = createString("fromCodeSystemCd");

    public final StringPath fromCodeSystemDescTxt = createString("fromCodeSystemDescTxt");

    public final StringPath fromDataType = createString("fromDataType");

    public final StringPath fromFormCd = createString("fromFormCd");

    public final StringPath fromQuestionDisplayName = createString("fromQuestionDisplayName");

    public final StringPath fromQuestionIdentifier = createString("fromQuestionIdentifier");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath rdbColumnNm = createString("rdbColumnNm");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath toCodeSet = createString("toCodeSet");

    public final StringPath toCodeSystemCd = createString("toCodeSystemCd");

    public final StringPath toCodeSystemDescTxt = createString("toCodeSystemDescTxt");

    public final StringPath toDataType = createString("toDataType");

    public final StringPath toFormCd = createString("toFormCd");

    public final StringPath toQuestionDisplayName = createString("toQuestionDisplayName");

    public final StringPath toQuestionIdentifier = createString("toQuestionIdentifier");

    public QLookupQuestion(String variable) {
        super(LookupQuestion.class, forVariable(variable));
    }

    public QLookupQuestion(Path<? extends LookupQuestion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLookupQuestion(PathMetadata metadata) {
        super(LookupQuestion.class, metadata);
    }

}

