package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCdfSubformImportLog is a Querydsl query type for CdfSubformImportLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCdfSubformImportLog extends EntityPathBase<CdfSubformImportLog> {

    private static final long serialVersionUID = 737277225L;

    public static final QCdfSubformImportLog cdfSubformImportLog = new QCdfSubformImportLog("cdfSubformImportLog");

    public final StringPath adminComment = createString("adminComment");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> importTime = createDateTime("importTime", java.time.Instant.class);

    public final NumberPath<Long> importVersionNbr = createNumber("importVersionNbr", Long.class);

    public final StringPath logMessageTxt = createString("logMessageTxt");

    public final StringPath processCd = createString("processCd");

    public final StringPath sourceNm = createString("sourceNm");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCdfSubformImportLog(String variable) {
        super(CdfSubformImportLog.class, forVariable(variable));
    }

    public QCdfSubformImportLog(Path<? extends CdfSubformImportLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCdfSubformImportLog(PathMetadata metadata) {
        super(CdfSubformImportLog.class, metadata);
    }

}

