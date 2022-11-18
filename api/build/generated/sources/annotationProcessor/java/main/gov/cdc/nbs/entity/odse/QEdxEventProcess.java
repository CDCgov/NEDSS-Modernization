package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEdxEventProcess is a Querydsl query type for EdxEventProcess
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxEventProcess extends EntityPathBase<EdxEventProcess> {

    private static final long serialVersionUID = -552848009L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEdxEventProcess edxEventProcess = new QEdxEventProcess("edxEventProcess");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath docEventTypeCd = createString("docEventTypeCd");

    public final NumberPath<Long> edxDocumentUid = createNumber("edxDocumentUid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNbsDocument nbsDocumentUid;

    public final QAct nbsEventUid;

    public final ComparablePath<Character> parsedInd = createComparable("parsedInd", Character.class);

    public final StringPath sourceEventId = createString("sourceEventId");

    public QEdxEventProcess(String variable) {
        this(EdxEventProcess.class, forVariable(variable), INITS);
    }

    public QEdxEventProcess(Path<? extends EdxEventProcess> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEdxEventProcess(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEdxEventProcess(PathMetadata metadata, PathInits inits) {
        this(EdxEventProcess.class, metadata, inits);
    }

    public QEdxEventProcess(Class<? extends EdxEventProcess> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsDocumentUid = inits.isInitialized("nbsDocumentUid") ? new QNbsDocument(forProperty("nbsDocumentUid"), inits.get("nbsDocumentUid")) : null;
        this.nbsEventUid = inits.isInitialized("nbsEventUid") ? new QAct(forProperty("nbsEventUid")) : null;
    }

}

