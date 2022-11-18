package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportSectionId is a Querydsl query type for ReportSectionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReportSectionId extends BeanPath<ReportSectionId> {

    private static final long serialVersionUID = 1402858677L;

    public static final QReportSectionId reportSectionId = new QReportSectionId("reportSectionId");

    public final NumberPath<Long> reportSectionUid = createNumber("reportSectionUid", Long.class);

    public final StringPath sectionCd = createString("sectionCd");

    public QReportSectionId(String variable) {
        super(ReportSectionId.class, forVariable(variable));
    }

    public QReportSectionId(Path<? extends ReportSectionId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportSectionId(PathMetadata metadata) {
        super(ReportSectionId.class, metadata);
    }

}

