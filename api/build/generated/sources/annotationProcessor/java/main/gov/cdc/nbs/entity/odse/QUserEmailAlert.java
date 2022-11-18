package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEmailAlert is a Querydsl query type for UserEmailAlert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEmailAlert extends EntityPathBase<UserEmailAlert> {

    private static final long serialVersionUID = -835053694L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEmailAlert userEmailAlert = new QUserEmailAlert("userEmailAlert");

    public final QAlertEmailMessage alertEmailMessageUid;

    public final StringPath emailId = createString("emailId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> nedssEntryUid = createNumber("nedssEntryUid", Long.class);

    public final NumberPath<Integer> seqNbr = createNumber("seqNbr", Integer.class);

    public QUserEmailAlert(String variable) {
        this(UserEmailAlert.class, forVariable(variable), INITS);
    }

    public QUserEmailAlert(Path<? extends UserEmailAlert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEmailAlert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEmailAlert(PathMetadata metadata, PathInits inits) {
        this(UserEmailAlert.class, metadata, inits);
    }

    public QUserEmailAlert(Class<? extends UserEmailAlert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertEmailMessageUid = inits.isInitialized("alertEmailMessageUid") ? new QAlertEmailMessage(forProperty("alertEmailMessageUid"), inits.get("alertEmailMessageUid")) : null;
    }

}

