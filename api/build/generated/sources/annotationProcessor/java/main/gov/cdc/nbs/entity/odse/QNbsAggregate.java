package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsAggregate is a Querydsl query type for NbsAggregate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsAggregate extends EntityPathBase<NbsAggregate> {

    private static final long serialVersionUID = -1730826441L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsAggregate nbsAggregate = new QNbsAggregate("nbsAggregate");

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

    public QNbsAggregate(String variable) {
        this(NbsAggregate.class, forVariable(variable), INITS);
    }

    public QNbsAggregate(Path<? extends NbsAggregate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsAggregate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsAggregate(PathMetadata metadata, PathInits inits) {
        this(NbsAggregate.class, metadata, inits);
    }

    public QNbsAggregate(Class<? extends NbsAggregate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
    }

}

