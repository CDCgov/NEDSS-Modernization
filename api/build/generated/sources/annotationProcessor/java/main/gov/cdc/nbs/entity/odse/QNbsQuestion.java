package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsQuestion is a Querydsl query type for NbsQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsQuestion extends EntityPathBase<NbsQuestion> {

    private static final long serialVersionUID = 1820687214L;

    public static final QNbsQuestion nbsQuestion = new QNbsQuestion("nbsQuestion");

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

    public QNbsQuestion(String variable) {
        super(NbsQuestion.class, forVariable(variable));
    }

    public QNbsQuestion(Path<? extends NbsQuestion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsQuestion(PathMetadata metadata) {
        super(NbsQuestion.class, metadata);
    }

}

