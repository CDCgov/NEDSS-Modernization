package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfirmationMethod is a Querydsl query type for ConfirmationMethod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfirmationMethod extends EntityPathBase<ConfirmationMethod> {

    private static final long serialVersionUID = -783107027L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfirmationMethod confirmationMethod = new QConfirmationMethod("confirmationMethod");

    public final StringPath confirmationMethodDescTxt = createString("confirmationMethodDescTxt");

    public final DateTimePath<java.time.Instant> confirmationMethodTime = createDateTime("confirmationMethodTime", java.time.Instant.class);

    public final QConfirmationMethodId id;

    public final QPublicHealthCase publicHealthCaseUid;

    public QConfirmationMethod(String variable) {
        this(ConfirmationMethod.class, forVariable(variable), INITS);
    }

    public QConfirmationMethod(Path<? extends ConfirmationMethod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfirmationMethod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfirmationMethod(PathMetadata metadata, PathInits inits) {
        this(ConfirmationMethod.class, metadata, inits);
    }

    public QConfirmationMethod(Class<? extends ConfirmationMethod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QConfirmationMethodId(forProperty("id")) : null;
        this.publicHealthCaseUid = inits.isInitialized("publicHealthCaseUid") ? new QPublicHealthCase(forProperty("publicHealthCaseUid"), inits.get("publicHealthCaseUid")) : null;
    }

}

