package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEdxDocument is a Querydsl query type for EdxDocument
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxDocument extends EntityPathBase<EdxDocument> {

    private static final long serialVersionUID = 2000606493L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEdxDocument edxDocument = new QEdxDocument("edxDocument");

    public final QAct actUid;

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath docTypeCd = createString("docTypeCd");

    public final NumberPath<Long> edxDocumentParentUid = createNumber("edxDocumentParentUid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNbsDocumentMetadatum nbsDocumentMetadataUid;

    public final StringPath originalDocTypeCd = createString("originalDocTypeCd");

    public final StringPath originalPayload = createString("originalPayload");

    public final StringPath payload = createString("payload");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QEdxDocument(String variable) {
        this(EdxDocument.class, forVariable(variable), INITS);
    }

    public QEdxDocument(Path<? extends EdxDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEdxDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEdxDocument(PathMetadata metadata, PathInits inits) {
        this(EdxDocument.class, metadata, inits);
    }

    public QEdxDocument(Class<? extends EdxDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actUid = inits.isInitialized("actUid") ? new QAct(forProperty("actUid")) : null;
        this.nbsDocumentMetadataUid = inits.isInitialized("nbsDocumentMetadataUid") ? new QNbsDocumentMetadatum(forProperty("nbsDocumentMetadataUid")) : null;
    }

}

