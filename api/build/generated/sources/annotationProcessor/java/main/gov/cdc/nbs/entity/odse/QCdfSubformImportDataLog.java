package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCdfSubformImportDataLog is a Querydsl query type for CdfSubformImportDataLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCdfSubformImportDataLog extends EntityPathBase<CdfSubformImportDataLog> {

    private static final long serialVersionUID = -1946158433L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCdfSubformImportDataLog cdfSubformImportDataLog = new QCdfSubformImportDataLog("cdfSubformImportDataLog");

    public final StringPath actionType = createString("actionType");

    public final QCdfSubformImportDataLogId id;

    public final QCdfSubformImportLog importLogUid;

    public final DateTimePath<java.time.Instant> importTime = createDateTime("importTime", java.time.Instant.class);

    public final StringPath logMessageTxt = createString("logMessageTxt");

    public final StringPath processCd = createString("processCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCdfSubformImportDataLog(String variable) {
        this(CdfSubformImportDataLog.class, forVariable(variable), INITS);
    }

    public QCdfSubformImportDataLog(Path<? extends CdfSubformImportDataLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCdfSubformImportDataLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCdfSubformImportDataLog(PathMetadata metadata, PathInits inits) {
        this(CdfSubformImportDataLog.class, metadata, inits);
    }

    public QCdfSubformImportDataLog(Class<? extends CdfSubformImportDataLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCdfSubformImportDataLogId(forProperty("id")) : null;
        this.importLogUid = inits.isInitialized("importLogUid") ? new QCdfSubformImportLog(forProperty("importLogUid")) : null;
    }

}

