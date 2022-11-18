package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsAnswer is a Querydsl query type for NbsAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsAnswer extends EntityPathBase<NbsAnswer> {

    private static final long serialVersionUID = -1087913658L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsAnswer nbsAnswer = new QNbsAnswer("nbsAnswer");

    public final QAct actUid;

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final NumberPath<Short> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Short.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> seqNbr = createNumber("seqNbr", Short.class);

    public QNbsAnswer(String variable) {
        this(NbsAnswer.class, forVariable(variable), INITS);
    }

    public QNbsAnswer(Path<? extends NbsAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsAnswer(PathMetadata metadata, PathInits inits) {
        this(NbsAnswer.class, metadata, inits);
    }

    public QNbsAnswer(Class<? extends NbsAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
    }

}

