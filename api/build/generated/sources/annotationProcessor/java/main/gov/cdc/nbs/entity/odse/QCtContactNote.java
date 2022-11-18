package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCtContactNote is a Querydsl query type for CtContactNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCtContactNote extends EntityPathBase<CtContactNote> {

    private static final long serialVersionUID = -1789489686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCtContactNote ctContactNote = new QCtContactNote("ctContactNote");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QCtContact ctContactUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath note = createString("note");

    public final ComparablePath<Character> privateIndCd = createComparable("privateIndCd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QCtContactNote(String variable) {
        this(CtContactNote.class, forVariable(variable), INITS);
    }

    public QCtContactNote(Path<? extends CtContactNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCtContactNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCtContactNote(PathMetadata metadata, PathInits inits) {
        this(CtContactNote.class, metadata, inits);
    }

    public QCtContactNote(Class<? extends CtContactNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ctContactUid = inits.isInitialized("ctContactUid") ? new QCtContact(forProperty("ctContactUid"), inits.get("ctContactUid")) : null;
    }

}

