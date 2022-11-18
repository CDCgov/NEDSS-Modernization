package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStateDefinedFieldDataHist is a Querydsl query type for StateDefinedFieldDataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStateDefinedFieldDataHist extends EntityPathBase<StateDefinedFieldDataHist> {

    private static final long serialVersionUID = 867147031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStateDefinedFieldDataHist stateDefinedFieldDataHist = new QStateDefinedFieldDataHist("stateDefinedFieldDataHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath businessObjectNm = createString("businessObjectNm");

    public final QStateDefinedFieldDataHistId id;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final StringPath ldfValue = createString("ldfValue");

    public QStateDefinedFieldDataHist(String variable) {
        this(StateDefinedFieldDataHist.class, forVariable(variable), INITS);
    }

    public QStateDefinedFieldDataHist(Path<? extends StateDefinedFieldDataHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStateDefinedFieldDataHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStateDefinedFieldDataHist(PathMetadata metadata, PathInits inits) {
        this(StateDefinedFieldDataHist.class, metadata, inits);
    }

    public QStateDefinedFieldDataHist(Class<? extends StateDefinedFieldDataHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStateDefinedFieldDataHistId(forProperty("id")) : null;
    }

}

