package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertLog is a Querydsl query type for AlertLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertLog extends EntityPathBase<AlertLog> {

    private static final long serialVersionUID = -594367553L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertLog alertLog = new QAlertLog("alertLog");

    public final StringPath activityLog = createString("activityLog");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final QAlert alertUid;

    public final StringPath eventLocalId = createString("eventLocalId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAlertLog(String variable) {
        this(AlertLog.class, forVariable(variable), INITS);
    }

    public QAlertLog(Path<? extends AlertLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertLog(PathMetadata metadata, PathInits inits) {
        this(AlertLog.class, metadata, inits);
    }

    public QAlertLog(Class<? extends AlertLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertUid = inits.isInitialized("alertUid") ? new QAlert(forProperty("alertUid")) : null;
    }

}

