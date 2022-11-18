package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChartReportMetadatum is a Querydsl query type for ChartReportMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChartReportMetadatum extends EntityPathBase<ChartReportMetadatum> {

    private static final long serialVersionUID = -919726097L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChartReportMetadatum chartReportMetadatum = new QChartReportMetadatum("chartReportMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath chartReportCd = createString("chartReportCd");

    public final StringPath chartReportDescTxt = createString("chartReportDescTxt");

    public final StringPath chartReportShortDescTxt = createString("chartReportShortDescTxt");

    public final QChartType chartTypeUid;

    public final ComparablePath<Character> defaultIndCd = createComparable("defaultIndCd", Character.class);

    public final StringPath externalClassNm = createString("externalClassNm");

    public final StringPath externalMethodNm = createString("externalMethodNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath objectNm = createString("objectNm");

    public final StringPath operationNm = createString("operationNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> securedByObjectOperation = createComparable("securedByObjectOperation", Character.class);

    public final ComparablePath<Character> securedIndCd = createComparable("securedIndCd", Character.class);

    public final StringPath xAxisTitle = createString("xAxisTitle");

    public final StringPath yAxisTitle = createString("yAxisTitle");

    public QChartReportMetadatum(String variable) {
        this(ChartReportMetadatum.class, forVariable(variable), INITS);
    }

    public QChartReportMetadatum(Path<? extends ChartReportMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChartReportMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChartReportMetadatum(PathMetadata metadata, PathInits inits) {
        this(ChartReportMetadatum.class, metadata, inits);
    }

    public QChartReportMetadatum(Class<? extends ChartReportMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chartTypeUid = inits.isInitialized("chartTypeUid") ? new QChartType(forProperty("chartTypeUid")) : null;
    }

}

