package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContactAnswerHist is a Querydsl query type for CtContactAnswerHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContactAnswerHist extends EntityPathBase<CtContactAnswerHist> {

    private static final long serialVersionUID = -318961672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContactAnswerHist ctContactAnswerHist = new QCtContactAnswerHist("ctContactAnswerHist");

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final NumberPath<Long> ctContactAnswerUid = createNumber("ctContactAnswerUid", Long.class);

    public final QCtContact ctContactUid;

    public final NumberPath<Long> ctContactVersionCtrlNbr = createNumber("ctContactVersionCtrlNbr", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final NumberPath<Short> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Short.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> seqNbr = createNumber("seqNbr", Short.class);

    public QCtContactAnswerHist(String variable) {
        this(CtContactAnswerHist.class, forVariable(variable), INITS);
    }

    public QCtContactAnswerHist(Path<? extends CtContactAnswerHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContactAnswerHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContactAnswerHist(PathMetadata metadata, PathInits inits) {
        this(CtContactAnswerHist.class, metadata, inits);
    }

    public QCtContactAnswerHist(Class<? extends CtContactAnswerHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ctContactUid = inits.isInitialized("ctContactUid") ? new QCtContact(forProperty("ctContactUid"), inits.get("ctContactUid")) : null;
    }

}

