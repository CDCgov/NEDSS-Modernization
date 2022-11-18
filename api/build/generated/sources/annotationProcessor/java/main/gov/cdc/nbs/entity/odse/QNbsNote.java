package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsNote is a Querydsl query type for NbsNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsNote extends EntityPathBase<NbsNote> {

    private static final long serialVersionUID = 714339194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsNote nbsNote = new QNbsNote("nbsNote");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath note = createString("note");

    public final QPublicHealthCase noteParentUid;

    public final ComparablePath<Character> privateIndCd = createComparable("privateIndCd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath typeCd = createString("typeCd");

    public QNbsNote(String variable) {
        this(NbsNote.class, forVariable(variable), INITS);
    }

    public QNbsNote(Path<? extends NbsNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsNote(PathMetadata metadata, PathInits inits) {
        this(NbsNote.class, metadata, inits);
    }

    public QNbsNote(Class<? extends NbsNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.noteParentUid = inits.isInitialized("noteParentUid") ? new QPublicHealthCase(forProperty("noteParentUid"), inits.get("noteParentUid")) : null;
    }

}

