package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportId is a Querydsl query type for ReportId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReportId extends BeanPath<ReportId> {

    private static final long serialVersionUID = 1658173670L;

    public static final QReportId reportId = new QReportId("reportId");

    public final NumberPath<Long> dataSourceUid = createNumber("dataSourceUid", Long.class);

    public final NumberPath<Long> reportUid = createNumber("reportUid", Long.class);

    public QReportId(String variable) {
        super(ReportId.class, forVariable(variable));
    }

    public QReportId(Path<? extends ReportId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportId(PathMetadata metadata) {
        super(ReportId.class, metadata);
    }

}

