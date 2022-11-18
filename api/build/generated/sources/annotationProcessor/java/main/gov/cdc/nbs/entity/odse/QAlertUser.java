package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertUser is a Querydsl query type for AlertUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertUser extends EntityPathBase<AlertUser> {

    private static final long serialVersionUID = -1245252944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertUser alertUser = new QAlertUser("alertUser");

    public final QAlert alertUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> nedssEntryId = createNumber("nedssEntryId", Long.class);

    public QAlertUser(String variable) {
        this(AlertUser.class, forVariable(variable), INITS);
    }

    public QAlertUser(Path<? extends AlertUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertUser(PathMetadata metadata, PathInits inits) {
        this(AlertUser.class, metadata, inits);
    }

    public QAlertUser(Class<? extends AlertUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertUid = inits.isInitialized("alertUid") ? new QAlert(forProperty("alertUid")) : null;
    }

}

