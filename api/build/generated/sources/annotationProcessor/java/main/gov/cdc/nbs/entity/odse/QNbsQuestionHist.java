package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsQuestionHist is a Querydsl query type for NbsQuestionHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsQuestionHist extends EntityPathBase<NbsQuestionHist> {

    private static final long serialVersionUID = 1837131696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsQuestionHist nbsQuestionHist = new QNbsQuestionHist("nbsQuestionHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> codeSetGroupId = createNumber("codeSetGroupId", Long.class);

    public final StringPath dataCd = createString("dataCd");

    public final StringPath dataLocation = createString("dataLocation");

    public final StringPath datamartColumnNm = createString("datamartColumnNm");

    public final StringPath dataType = createString("dataType");

    public final StringPath dataUseCd = createString("dataUseCd");

    public final StringPath defaultValue = createString("defaultValue");

    public final ComparablePath<Character> futureDateIndCd = createComparable("futureDateIndCd", Character.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath legacyDataLocation = createString("legacyDataLocation");

    public final QNbsQuestion nbsQuestionUid;

    public final StringPath partTypeCd = createString("partTypeCd");

    public final NumberPath<Integer> questionGroupSeqNbr = createNumber("questionGroupSeqNbr", Integer.class);

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath questionLabel = createString("questionLabel");

    public final StringPath questionOid = createString("questionOid");

    public final StringPath questionOidSystemTxt = createString("questionOidSystemTxt");

    public final StringPath questionToolTip = createString("questionToolTip");

    public final StringPath questionUnitIdentifier = createString("questionUnitIdentifier");

    public final ComparablePath<Character> repeatsIndCd = createComparable("repeatsIndCd", Character.class);

    public final StringPath unitParentIdentifier = createString("unitParentIdentifier");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public QNbsQuestionHist(String variable) {
        this(NbsQuestionHist.class, forVariable(variable), INITS);
    }

    public QNbsQuestionHist(Path<? extends NbsQuestionHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsQuestionHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsQuestionHist(PathMetadata metadata, PathInits inits) {
        this(NbsQuestionHist.class, metadata, inits);
    }

    public QNbsQuestionHist(Class<? extends NbsQuestionHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
    }

}

