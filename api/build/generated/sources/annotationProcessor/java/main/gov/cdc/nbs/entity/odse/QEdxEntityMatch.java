package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEdxEntityMatch is a Querydsl query type for EdxEntityMatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxEntityMatch extends EntityPathBase<EdxEntityMatch> {

    private static final long serialVersionUID = -435199904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEdxEntityMatch edxEntityMatch = new QEdxEntityMatch("edxEntityMatch");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath matchString = createString("matchString");

    public final NumberPath<Long> matchStringHashcode = createNumber("matchStringHashcode", Long.class);

    public final QNBSEntity NBSEntityUid;

    public final StringPath typeCd = createString("typeCd");

    public QEdxEntityMatch(String variable) {
        this(EdxEntityMatch.class, forVariable(variable), INITS);
    }

    public QEdxEntityMatch(Path<? extends EdxEntityMatch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEdxEntityMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEdxEntityMatch(PathMetadata metadata, PathInits inits) {
        this(EdxEntityMatch.class, metadata, inits);
    }

    public QEdxEntityMatch(Class<? extends EdxEntityMatch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.NBSEntityUid = inits.isInitialized("NBSEntityUid") ? new QNBSEntity(forProperty("NBSEntityUid")) : null;
    }

}

