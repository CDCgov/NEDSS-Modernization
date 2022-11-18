package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpdatedNotificationId is a Querydsl query type for UpdatedNotificationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUpdatedNotificationId extends BeanPath<UpdatedNotificationId> {

    private static final long serialVersionUID = 1204588266L;

    public static final QUpdatedNotificationId updatedNotificationId = new QUpdatedNotificationId("updatedNotificationId");

    public final NumberPath<Long> notificationUid = createNumber("notificationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QUpdatedNotificationId(String variable) {
        super(UpdatedNotificationId.class, forVariable(variable));
    }

    public QUpdatedNotificationId(Path<? extends UpdatedNotificationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpdatedNotificationId(PathMetadata metadata) {
        super(UpdatedNotificationId.class, metadata);
    }

}

