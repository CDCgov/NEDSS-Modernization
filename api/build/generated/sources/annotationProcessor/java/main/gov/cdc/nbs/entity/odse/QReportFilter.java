package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportFilter is a Querydsl query type for ReportFilter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportFilter extends EntityPathBase<ReportFilter> {

    private static final long serialVersionUID = 423467459L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportFilter reportFilter = new QReportFilter("reportFilter");

    public final QDataSourceColumn columnUid;

    public final QFilterCode filterUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxValueCnt = createNumber("maxValueCnt", Integer.class);

    public final NumberPath<Integer> minValueCnt = createNumber("minValueCnt", Integer.class);

    public final QReport report;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public QReportFilter(String variable) {
        this(ReportFilter.class, forVariable(variable), INITS);
    }

    public QReportFilter(Path<? extends ReportFilter> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportFilter(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportFilter(PathMetadata metadata, PathInits inits) {
        this(ReportFilter.class, metadata, inits);
    }

    public QReportFilter(Class<? extends ReportFilter> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.columnUid = inits.isInitialized("columnUid") ? new QDataSourceColumn(forProperty("columnUid"), inits.get("columnUid")) : null;
        this.filterUid = inits.isInitialized("filterUid") ? new QFilterCode(forProperty("filterUid")) : null;
        this.report = inits.isInitialized("report") ? new QReport(forProperty("report"), inits.get("report")) : null;
    }

}

