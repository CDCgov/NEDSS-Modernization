package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotificationHistId is a Querydsl query type for NotificationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNotificationHistId extends BeanPath<NotificationHistId> {

    private static final long serialVersionUID = 1025486687L;

    public static final QNotificationHistId notificationHistId = new QNotificationHistId("notificationHistId");

    public final NumberPath<Long> notificationUid = createNumber("notificationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QNotificationHistId(String variable) {
        super(NotificationHistId.class, forVariable(variable));
    }

    public QNotificationHistId(Path<? extends NotificationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotificationHistId(PathMetadata metadata) {
        super(NotificationHistId.class, metadata);
    }

}

