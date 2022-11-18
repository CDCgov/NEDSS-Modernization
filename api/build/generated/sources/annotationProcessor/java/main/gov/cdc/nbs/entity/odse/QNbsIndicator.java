package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsIndicator is a Querydsl query type for NbsIndicator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsIndicator extends EntityPathBase<NbsIndicator> {

    private static final long serialVersionUID = 1676963719L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsIndicator nbsIndicator = new QNbsIndicator("nbsIndicator");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath code = createString("code");

    public final StringPath descTxt = createString("descTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QNbsQuestion nbsQuestionUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final StringPath toolTip = createString("toolTip");

    public QNbsIndicator(String variable) {
        this(NbsIndicator.class, forVariable(variable), INITS);
    }

    public QNbsIndicator(Path<? extends NbsIndicator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsIndicator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsIndicator(PathMetadata metadata, PathInits inits) {
        this(NbsIndicator.class, metadata, inits);
    }

    public QNbsIndicator(Class<? extends NbsIndicator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
    }

}

