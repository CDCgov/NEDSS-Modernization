package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterviewHist is a Querydsl query type for InterviewHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterviewHist extends EntityPathBase<InterviewHist> {

    private static final long serialVersionUID = -1094863060L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterviewHist interviewHist = new QInterviewHist("interviewHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> interviewDate = createDateTime("interviewDate", java.time.Instant.class);

    public final StringPath intervieweeRoleCd = createString("intervieweeRoleCd");

    public final StringPath interviewLocCd = createString("interviewLocCd");

    public final StringPath interviewStatusCd = createString("interviewStatusCd");

    public final StringPath interviewTypeCd = createString("interviewTypeCd");

    public final QInterview interviewUid;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QInterviewHist(String variable) {
        this(InterviewHist.class, forVariable(variable), INITS);
    }

    public QInterviewHist(Path<? extends InterviewHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterviewHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterviewHist(PathMetadata metadata, PathInits inits) {
        this(InterviewHist.class, metadata, inits);
    }

    public QInterviewHist(Class<? extends InterviewHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.interviewUid = inits.isInitialized("interviewUid") ? new QInterview(forProperty("interviewUid"), inits.get("interviewUid")) : null;
    }

}

