package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsActEntityHist is a Querydsl query type for NbsActEntityHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsActEntityHist extends EntityPathBase<NbsActEntityHist> {

    private static final long serialVersionUID = 1889536335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsActEntityHist nbsActEntityHist = new QNbsActEntityHist("nbsActEntityHist");

    public final QAct actUid;

    public final NumberPath<Short> actVersionCtrlNbr = createNumber("actVersionCtrlNbr", Short.class);

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Short> entityVersionCtrlNbr = createNumber("entityVersionCtrlNbr", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Long> nbsActEntityUid = createNumber("nbsActEntityUid", Long.class);

    public final QNBSEntity NBSEntityUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public QNbsActEntityHist(String variable) {
        this(NbsActEntityHist.class, forVariable(variable), INITS);
    }

    public QNbsActEntityHist(Path<? extends NbsActEntityHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsActEntityHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsActEntityHist(PathMetadata metadata, PathInits inits) {
        this(NbsActEntityHist.class, metadata, inits);
    }

    public QNbsActEntityHist(Class<? extends NbsActEntityHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.NBSEntityUid = inits.isInitialized("NBSEntityUid") ? new QNBSEntity(forProperty("NBSEntityUid")) : null;
    }

}

