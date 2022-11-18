package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsActEntity is a Querydsl query type for NbsActEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsActEntity extends EntityPathBase<NbsActEntity> {

    private static final long serialVersionUID = 1555874957L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsActEntity nbsActEntity = new QNbsActEntity("nbsActEntity");

    public final QAct actUid;

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Short> entityVersionCtrlNbr = createNumber("entityVersionCtrlNbr", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QNBSEntity NBSEntityUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public QNbsActEntity(String variable) {
        this(NbsActEntity.class, forVariable(variable), INITS);
    }

    public QNbsActEntity(Path<? extends NbsActEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsActEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsActEntity(PathMetadata metadata, PathInits inits) {
        this(NbsActEntity.class, metadata, inits);
    }

    public QNbsActEntity(Class<? extends NbsActEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.NBSEntityUid = inits.isInitialized("NBSEntityUid") ? new QNBSEntity(forProperty("NBSEntityUid")) : null;
    }

}

