package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDsmAlgorithmHist is a Querydsl query type for DsmAlgorithmHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsmAlgorithmHist extends EntityPathBase<DsmAlgorithmHist> {

    private static final long serialVersionUID = 572594058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDsmAlgorithmHist dsmAlgorithmHist = new QDsmAlgorithmHist("dsmAlgorithmHist");

    public final StringPath adminComment = createString("adminComment");

    public final StringPath algorithmNm = createString("algorithmNm");

    public final StringPath algorithmPayload = createString("algorithmPayload");

    public final StringPath applyTo = createString("applyTo");

    public final StringPath conditionList = createString("conditionList");

    public final QDsmAlgorithm dsmAlgorithmUid;

    public final StringPath eventAction = createString("eventAction");

    public final StringPath eventType = createString("eventType");

    public final StringPath frequency = createString("frequency");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath reportingSystemList = createString("reportingSystemList");

    public final StringPath resultedTestList = createString("resultedTestList");

    public final StringPath sendingSystemList = createString("sendingSystemList");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public QDsmAlgorithmHist(String variable) {
        this(DsmAlgorithmHist.class, forVariable(variable), INITS);
    }

    public QDsmAlgorithmHist(Path<? extends DsmAlgorithmHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDsmAlgorithmHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDsmAlgorithmHist(PathMetadata metadata, PathInits inits) {
        this(DsmAlgorithmHist.class, metadata, inits);
    }

    public QDsmAlgorithmHist(Class<? extends DsmAlgorithmHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dsmAlgorithmUid = inits.isInitialized("dsmAlgorithmUid") ? new QDsmAlgorithm(forProperty("dsmAlgorithmUid")) : null;
    }

}

