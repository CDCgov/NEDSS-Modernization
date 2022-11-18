package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUpdatedNotification is a Querydsl query type for UpdatedNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUpdatedNotification extends EntityPathBase<UpdatedNotification> {

    private static final long serialVersionUID = 390079855L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUpdatedNotification updatedNotification = new QUpdatedNotification("updatedNotification");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath caseClassCd = createString("caseClassCd");

    public final ComparablePath<Character> caseStatusChgInd = createComparable("caseStatusChgInd", Character.class);

    public final QUpdatedNotificationId id;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QNotification notificationUid;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QUpdatedNotification(String variable) {
        this(UpdatedNotification.class, forVariable(variable), INITS);
    }

    public QUpdatedNotification(Path<? extends UpdatedNotification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUpdatedNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUpdatedNotification(PathMetadata metadata, PathInits inits) {
        this(UpdatedNotification.class, metadata, inits);
    }

    public QUpdatedNotification(Class<? extends UpdatedNotification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QUpdatedNotificationId(forProperty("id")) : null;
        this.notificationUid = inits.isInitialized("notificationUid") ? new QNotification(forProperty("notificationUid"), inits.get("notificationUid")) : null;
    }

}

