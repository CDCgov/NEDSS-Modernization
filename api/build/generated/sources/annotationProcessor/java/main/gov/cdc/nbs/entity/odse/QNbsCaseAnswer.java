package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsCaseAnswer is a Querydsl query type for NbsCaseAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsCaseAnswer extends EntityPathBase<NbsCaseAnswer> {

    private static final long serialVersionUID = -491650922L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsCaseAnswer nbsCaseAnswer = new QNbsCaseAnswer("nbsCaseAnswer");

    public final QAct actUid;

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QNbsQuestion nbsQuestionUid;

    public final NumberPath<Integer> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Integer.class);

    public final QNbsTableMetadatum nbsTableMetadataUid;

    public final NumberPath<Integer> nbsUiMetadataVerCtrlNbr = createNumber("nbsUiMetadataVerCtrlNbr", Integer.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Integer> seqNbr = createNumber("seqNbr", Integer.class);

    public QNbsCaseAnswer(String variable) {
        this(NbsCaseAnswer.class, forVariable(variable), INITS);
    }

    public QNbsCaseAnswer(Path<? extends NbsCaseAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsCaseAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsCaseAnswer(PathMetadata metadata, PathInits inits) {
        this(NbsCaseAnswer.class, metadata, inits);
    }

    public QNbsCaseAnswer(Class<? extends NbsCaseAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
        this.nbsTableMetadataUid = inits.isInitialized("nbsTableMetadataUid") ? new QNbsTableMetadatum(forProperty("nbsTableMetadataUid"), inits.get("nbsTableMetadataUid")) : null;
    }

}

