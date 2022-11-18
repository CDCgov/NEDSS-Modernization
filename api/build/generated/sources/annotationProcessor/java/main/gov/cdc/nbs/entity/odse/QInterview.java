package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterview is a Querydsl query type for Interview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterview extends EntityPathBase<Interview> {

    private static final long serialVersionUID = 720234730L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterview interview = new QInterview("interview");

    public final QAct act;

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> interviewDate = createDateTime("interviewDate", java.time.Instant.class);

    public final StringPath intervieweeRoleCd = createString("intervieweeRoleCd");

    public final StringPath interviewLocCd = createString("interviewLocCd");

    public final StringPath interviewStatusCd = createString("interviewStatusCd");

    public final StringPath interviewTypeCd = createString("interviewTypeCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QInterview(String variable) {
        this(Interview.class, forVariable(variable), INITS);
    }

    public QInterview(Path<? extends Interview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterview(PathMetadata metadata, PathInits inits) {
        this(Interview.class, metadata, inits);
    }

    public QInterview(Class<? extends Interview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.act = inits.isInitialized("act") ? new QAct(forProperty("act")) : null;
    }

}

