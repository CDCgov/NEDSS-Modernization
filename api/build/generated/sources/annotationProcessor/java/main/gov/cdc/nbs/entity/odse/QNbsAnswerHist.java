package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsAnswerHist is a Querydsl query type for NbsAnswerHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsAnswerHist extends EntityPathBase<NbsAnswerHist> {

    private static final long serialVersionUID = 2002518408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsAnswerHist nbsAnswerHist = new QNbsAnswerHist("nbsAnswerHist");

    public final QAct actUid;

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsAnswerUid = createNumber("nbsAnswerUid", Long.class);

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final NumberPath<Short> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Short.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> seqNbr = createNumber("seqNbr", Short.class);

    public QNbsAnswerHist(String variable) {
        this(NbsAnswerHist.class, forVariable(variable), INITS);
    }

    public QNbsAnswerHist(Path<? extends NbsAnswerHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsAnswerHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsAnswerHist(PathMetadata metadata, PathInits inits) {
        this(NbsAnswerHist.class, metadata, inits);
    }

    public QNbsAnswerHist(Class<? extends NbsAnswerHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
    }

}

