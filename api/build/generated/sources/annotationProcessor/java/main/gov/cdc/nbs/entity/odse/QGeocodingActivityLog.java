package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGeocodingActivityLog is a Querydsl query type for GeocodingActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeocodingActivityLog extends EntityPathBase<GeocodingActivityLog> {

    private static final long serialVersionUID = 1356767665L;

    public static final QGeocodingActivityLog geocodingActivityLog = new QGeocodingActivityLog("geocodingActivityLog");

    public final DateTimePath<java.time.Instant> batchEndTime = createDateTime("batchEndTime", java.time.Instant.class);

    public final StringPath batchRunMode = createString("batchRunMode");

    public final DateTimePath<java.time.Instant> batchStartTime = createDateTime("batchStartTime", java.time.Instant.class);

    public final StringPath completedInd = createString("completedInd");

    public final StringPath completionReason = createString("completionReason");

    public final NumberPath<Integer> errorNbr = createNumber("errorNbr", Integer.class);

    public final NumberPath<Integer> errorRecordNbr = createNumber("errorRecordNbr", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> multiMatchNbr = createNumber("multiMatchNbr", Integer.class);

    public final NumberPath<Integer> singleMatchNbr = createNumber("singleMatchNbr", Integer.class);

    public final NumberPath<Integer> totalNbr = createNumber("totalNbr", Integer.class);

    public final NumberPath<Integer> zeroMatchNbr = createNumber("zeroMatchNbr", Integer.class);

    public QGeocodingActivityLog(String variable) {
        super(GeocodingActivityLog.class, forVariable(variable));
    }

    public QGeocodingActivityLog(Path<? extends GeocodingActivityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeocodingActivityLog(PathMetadata metadata) {
        super(GeocodingActivityLog.class, metadata);
    }

}

