package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLookupAnswer is a Querydsl query type for LookupAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLookupAnswer extends EntityPathBase<LookupAnswer> {

    private static final long serialVersionUID = -764176945L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLookupAnswer lookupAnswer = new QLookupAnswer("lookupAnswer");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath fromAnsDisplayNm = createString("fromAnsDisplayNm");

    public final StringPath fromAnswerCode = createString("fromAnswerCode");

    public final StringPath fromCodeSystemCd = createString("fromCodeSystemCd");

    public final StringPath fromCodeSystemDescTxt = createString("fromCodeSystemDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QLookupQuestion lookupQuestionUid;

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath toAnsDisplayNm = createString("toAnsDisplayNm");

    public final StringPath toAnswerCode = createString("toAnswerCode");

    public final StringPath toCodeSystemCd = createString("toCodeSystemCd");

    public final StringPath toCodeSystemDescTxt = createString("toCodeSystemDescTxt");

    public QLookupAnswer(String variable) {
        this(LookupAnswer.class, forVariable(variable), INITS);
    }

    public QLookupAnswer(Path<? extends LookupAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLookupAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLookupAnswer(PathMetadata metadata, PathInits inits) {
        this(LookupAnswer.class, metadata, inits);
    }

    public QLookupAnswer(Class<? extends LookupAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lookupQuestionUid = inits.isInitialized("lookupQuestionUid") ? new QLookupQuestion(forProperty("lookupQuestionUid")) : null;
    }

}

