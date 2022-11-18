package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEdxActivityDetailLog is a Querydsl query type for EdxActivityDetailLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxActivityDetailLog extends EntityPathBase<EdxActivityDetailLog> {

    private static final long serialVersionUID = 944730402L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEdxActivityDetailLog edxActivityDetailLog = new QEdxActivityDetailLog("edxActivityDetailLog");

    public final QEdxActivityLog edxActivityLogUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logComment = createString("logComment");

    public final StringPath logType = createString("logType");

    public final StringPath recordId = createString("recordId");

    public final StringPath recordNm = createString("recordNm");

    public final StringPath recordType = createString("recordType");

    public QEdxActivityDetailLog(String variable) {
        this(EdxActivityDetailLog.class, forVariable(variable), INITS);
    }

    public QEdxActivityDetailLog(Path<? extends EdxActivityDetailLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEdxActivityDetailLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEdxActivityDetailLog(PathMetadata metadata, PathInits inits) {
        this(EdxActivityDetailLog.class, metadata, inits);
    }

    public QEdxActivityDetailLog(Class<? extends EdxActivityDetailLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.edxActivityLogUid = inits.isInitialized("edxActivityLogUid") ? new QEdxActivityLog(forProperty("edxActivityLogUid")) : null;
    }

}

