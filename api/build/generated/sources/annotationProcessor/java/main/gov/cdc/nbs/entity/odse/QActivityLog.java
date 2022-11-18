package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivityLog is a Querydsl query type for ActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityLog extends EntityPathBase<ActivityLog> {

    private static final long serialVersionUID = 1583090398L;

    public static final QActivityLog activityLog = new QActivityLog("activityLog");

    public final StringPath actionTxt = createString("actionTxt");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath docNm = createString("docNm");

    public final StringPath docType = createString("docType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath messageTxt = createString("messageTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath sourceId = createString("sourceId");

    public final StringPath sourceTypeCd = createString("sourceTypeCd");

    public final StringPath targetId = createString("targetId");

    public final StringPath targetTypeCd = createString("targetTypeCd");

    public QActivityLog(String variable) {
        super(ActivityLog.class, forVariable(variable));
    }

    public QActivityLog(Path<? extends ActivityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivityLog(PathMetadata metadata) {
        super(ActivityLog.class, metadata);
    }

}

