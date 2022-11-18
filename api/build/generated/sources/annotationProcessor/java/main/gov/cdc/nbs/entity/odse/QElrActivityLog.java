package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QElrActivityLog is a Querydsl query type for ElrActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QElrActivityLog extends EntityPathBase<ElrActivityLog> {

    private static final long serialVersionUID = -1443765695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QElrActivityLog elrActivityLog = new QElrActivityLog("elrActivityLog");

    public final StringPath detailTxt = createString("detailTxt");

    public final StringPath fillerNbr = createString("fillerNbr");

    public final QElrActivityLogId id;

    public final StringPath id1 = createString("id1");

    public final StringPath odsObservationUid = createString("odsObservationUid");

    public final StringPath processCd = createString("processCd");

    public final DateTimePath<java.time.Instant> processTime = createDateTime("processTime", java.time.Instant.class);

    public final StringPath reportFacNm = createString("reportFacNm");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final StringPath subjectNm = createString("subjectNm");

    public QElrActivityLog(String variable) {
        this(ElrActivityLog.class, forVariable(variable), INITS);
    }

    public QElrActivityLog(Path<? extends ElrActivityLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QElrActivityLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QElrActivityLog(PathMetadata metadata, PathInits inits) {
        this(ElrActivityLog.class, metadata, inits);
    }

    public QElrActivityLog(Class<? extends ElrActivityLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QElrActivityLogId(forProperty("id")) : null;
    }

}

