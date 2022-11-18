package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConfirmationMethodHist is a Querydsl query type for ConfirmationMethodHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfirmationMethodHist extends EntityPathBase<ConfirmationMethodHist> {

    private static final long serialVersionUID = 875639023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConfirmationMethodHist confirmationMethodHist = new QConfirmationMethodHist("confirmationMethodHist");

    public final StringPath confirmationMethodDescTxt = createString("confirmationMethodDescTxt");

    public final DateTimePath<java.time.Instant> confirmationMethodTime = createDateTime("confirmationMethodTime", java.time.Instant.class);

    public final QConfirmationMethodHistId id;

    public QConfirmationMethodHist(String variable) {
        this(ConfirmationMethodHist.class, forVariable(variable), INITS);
    }

    public QConfirmationMethodHist(Path<? extends ConfirmationMethodHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConfirmationMethodHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConfirmationMethodHist(PathMetadata metadata, PathInits inits) {
        this(ConfirmationMethodHist.class, metadata, inits);
    }

    public QConfirmationMethodHist(Class<? extends ConfirmationMethodHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QConfirmationMethodHistId(forProperty("id")) : null;
    }

}

