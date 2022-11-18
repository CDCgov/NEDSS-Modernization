package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsCaseAnswerHist is a Querydsl query type for NbsCaseAnswerHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsCaseAnswerHist extends EntityPathBase<NbsCaseAnswerHist> {

    private static final long serialVersionUID = 1108744408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsCaseAnswerHist nbsCaseAnswerHist = new QNbsCaseAnswerHist("nbsCaseAnswerHist");

    public final QAct actUid;

    public final NumberPath<Short> actVersionCtrlNbr = createNumber("actVersionCtrlNbr", Short.class);

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerLargeTxt = createString("answerLargeTxt");

    public final StringPath answerTxt = createString("answerTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsCaseAnswerUid = createNumber("nbsCaseAnswerUid", Long.class);

    public final QNbsQuestion nbsQuestionUid;

    public final NumberPath<Integer> nbsQuestionVersionCtrlNbr = createNumber("nbsQuestionVersionCtrlNbr", Integer.class);

    public final NumberPath<Long> nbsTableMetadataUid = createNumber("nbsTableMetadataUid", Long.class);

    public final NumberPath<Integer> nbsUiMetadataVerCtrlNbr = createNumber("nbsUiMetadataVerCtrlNbr", Integer.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Integer> seqNbr = createNumber("seqNbr", Integer.class);

    public QNbsCaseAnswerHist(String variable) {
        this(NbsCaseAnswerHist.class, forVariable(variable), INITS);
    }

    public QNbsCaseAnswerHist(Path<? extends NbsCaseAnswerHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsCaseAnswerHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsCaseAnswerHist(PathMetadata metadata, PathInits inits) {
        this(NbsCaseAnswerHist.class, metadata, inits);
    }

    public QNbsCaseAnswerHist(Class<? extends NbsCaseAnswerHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
    }

}

