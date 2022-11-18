package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDsmAlgorithm is a Querydsl query type for DsmAlgorithm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDsmAlgorithm extends EntityPathBase<DsmAlgorithm> {

    private static final long serialVersionUID = 2019305544L;

    public static final QDsmAlgorithm dsmAlgorithm = new QDsmAlgorithm("dsmAlgorithm");

    public final StringPath adminComment = createString("adminComment");

    public final StringPath algorithmNm = createString("algorithmNm");

    public final StringPath algorithmPayload = createString("algorithmPayload");

    public final StringPath applyTo = createString("applyTo");

    public final StringPath conditionList = createString("conditionList");

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

    public QDsmAlgorithm(String variable) {
        super(DsmAlgorithm.class, forVariable(variable));
    }

    public QDsmAlgorithm(Path<? extends DsmAlgorithm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDsmAlgorithm(PathMetadata metadata) {
        super(DsmAlgorithm.class, metadata);
    }

}

