package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContactAttachment is a Querydsl query type for CtContactAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContactAttachment extends EntityPathBase<CtContactAttachment> {

    private static final long serialVersionUID = 1053416731L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContactAttachment ctContactAttachment = new QCtContactAttachment("ctContactAttachment");

    public final ArrayPath<byte[], Byte> attachment = createArray("attachment", byte[].class);

    public final QCtContact ctContactUid;

    public final StringPath descTxt = createString("descTxt");

    public final StringPath fileNmTxt = createString("fileNmTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public QCtContactAttachment(String variable) {
        this(CtContactAttachment.class, forVariable(variable), INITS);
    }

    public QCtContactAttachment(Path<? extends CtContactAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContactAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContactAttachment(PathMetadata metadata, PathInits inits) {
        this(CtContactAttachment.class, metadata, inits);
    }

    public QCtContactAttachment(Class<? extends CtContactAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ctContactUid = inits.isInitialized("ctContactUid") ? new QCtContact(forProperty("ctContactUid"), inits.get("ctContactUid")) : null;
    }

}

