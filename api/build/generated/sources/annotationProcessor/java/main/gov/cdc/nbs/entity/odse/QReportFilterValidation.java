package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportFilterValidation is a Querydsl query type for ReportFilterValidation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportFilterValidation extends EntityPathBase<ReportFilterValidation> {

    private static final long serialVersionUID = -29383012L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportFilterValidation reportFilterValidation = new QReportFilterValidation("reportFilterValidation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reportFilterInd = createString("reportFilterInd");

    public final QReportFilter reportFilterUid;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QReportFilterValidation(String variable) {
        this(ReportFilterValidation.class, forVariable(variable), INITS);
    }

    public QReportFilterValidation(Path<? extends ReportFilterValidation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportFilterValidation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportFilterValidation(PathMetadata metadata, PathInits inits) {
        this(ReportFilterValidation.class, metadata, inits);
    }

    public QReportFilterValidation(Class<? extends ReportFilterValidation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reportFilterUid = inits.isInitialized("reportFilterUid") ? new QReportFilter(forProperty("reportFilterUid"), inits.get("reportFilterUid")) : null;
    }

}

