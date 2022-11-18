package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsAttachment is a Querydsl query type for NbsAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsAttachment extends EntityPathBase<NbsAttachment> {

    private static final long serialVersionUID = -920070485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsAttachment nbsAttachment = new QNbsAttachment("nbsAttachment");

    public final ArrayPath<byte[], Byte> attachment = createArray("attachment", byte[].class);

    public final QAct attachmentParentUid;

    public final StringPath descTxt = createString("descTxt");

    public final StringPath fileNmTxt = createString("fileNmTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public QNbsAttachment(String variable) {
        this(NbsAttachment.class, forVariable(variable), INITS);
    }

    public QNbsAttachment(Path<? extends NbsAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsAttachment(PathMetadata metadata, PathInits inits) {
        this(NbsAttachment.class, metadata, inits);
    }

    public QNbsAttachment(Class<? extends NbsAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attachmentParentUid = inits.isInitialized("attachmentParentUid") ? new QAct(forProperty("attachmentParentUid")) : null;
    }

}

