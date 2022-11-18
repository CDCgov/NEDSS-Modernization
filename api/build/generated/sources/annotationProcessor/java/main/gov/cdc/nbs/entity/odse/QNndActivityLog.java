package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNndActivityLog is a Querydsl query type for NndActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNndActivityLog extends EntityPathBase<NndActivityLog> {

    private static final long serialVersionUID = 202177192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNndActivityLog nndActivityLog = new QNndActivityLog("nndActivityLog");

    public final StringPath errorMessageTxt = createString("errorMessageTxt");

    public final QNndActivityLogId id;

    public final StringPath localId = createString("localId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath service = createString("service");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QNndActivityLog(String variable) {
        this(NndActivityLog.class, forVariable(variable), INITS);
    }

    public QNndActivityLog(Path<? extends NndActivityLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNndActivityLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNndActivityLog(PathMetadata metadata, PathInits inits) {
        this(NndActivityLog.class, metadata, inits);
    }

    public QNndActivityLog(Class<? extends NndActivityLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QNndActivityLogId(forProperty("id")) : null;
    }

}

