package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportSortColumn is a Querydsl query type for ReportSortColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportSortColumn extends EntityPathBase<ReportSortColumn> {

    private static final long serialVersionUID = -1071302817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportSortColumn reportSortColumn = new QReportSortColumn("reportSortColumn");

    public final NumberPath<Long> columnUid = createNumber("columnUid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReport report;

    public final StringPath reportSortOrderCode = createString("reportSortOrderCode");

    public final NumberPath<Integer> reportSortSequenceNum = createNumber("reportSortSequenceNum", Integer.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QReportSortColumn(String variable) {
        this(ReportSortColumn.class, forVariable(variable), INITS);
    }

    public QReportSortColumn(Path<? extends ReportSortColumn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportSortColumn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportSortColumn(PathMetadata metadata, PathInits inits) {
        this(ReportSortColumn.class, metadata, inits);
    }

    public QReportSortColumn(Class<? extends ReportSortColumn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.report = inits.isInitialized("report") ? new QReport(forProperty("report"), inits.get("report")) : null;
    }

}

