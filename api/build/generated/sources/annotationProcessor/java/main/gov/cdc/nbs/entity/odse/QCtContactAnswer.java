package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContactAnswer is a Querydsl query type for CtContactAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContactAnswer extends EntityPathBase<CtContactAnswer> {

    private static final long serialVersionUID = -2085781578L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContactAnswer ctContactAnswer = new QCtContactAnswer("ctContactAnswer");

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final QCtContact ctContactUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final NumberPath<Short> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Short.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> seqNbr = createNumber("seqNbr", Short.class);

    public QCtContactAnswer(String variable) {
        this(CtContactAnswer.class, forVariable(variable), INITS);
    }

    public QCtContactAnswer(Path<? extends CtContactAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContactAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContactAnswer(PathMetadata metadata, PathInits inits) {
        this(CtContactAnswer.class, metadata, inits);
    }

    public QCtContactAnswer(Class<? extends CtContactAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ctContactUid = inits.isInitialized("ctContactUid") ? new QCtContact(forProperty("ctContactUid"), inits.get("ctContactUid")) : null;
    }

}

