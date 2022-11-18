package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeduplicationActivityLog is a Querydsl query type for DeduplicationActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeduplicationActivityLog extends EntityPathBase<DeduplicationActivityLog> {

    private static final long serialVersionUID = -1664834337L;

    public static final QDeduplicationActivityLog deduplicationActivityLog = new QDeduplicationActivityLog("deduplicationActivityLog");

    public final DateTimePath<java.time.Instant> batchEndTime = createDateTime("batchEndTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> batchStartTime = createDateTime("batchStartTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> mergedRecordsIdentifiedNbr = createNumber("mergedRecordsIdentifiedNbr", Short.class);

    public final NumberPath<Short> mergedRecordsSurvivedNbr = createNumber("mergedRecordsSurvivedNbr", Short.class);

    public final ComparablePath<Character> overrideInd = createComparable("overrideInd", Character.class);

    public final StringPath processException = createString("processException");

    public final StringPath processType = createString("processType");

    public final NumberPath<Integer> similarGroupNbr = createNumber("similarGroupNbr", Integer.class);

    public QDeduplicationActivityLog(String variable) {
        super(DeduplicationActivityLog.class, forVariable(variable));
    }

    public QDeduplicationActivityLog(Path<? extends DeduplicationActivityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeduplicationActivityLog(PathMetadata metadata) {
        super(DeduplicationActivityLog.class, metadata);
    }

}

