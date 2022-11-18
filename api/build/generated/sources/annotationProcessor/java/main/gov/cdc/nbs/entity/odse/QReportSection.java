package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportSection is a Querydsl query type for ReportSection
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportSection extends EntityPathBase<ReportSection> {

    private static final long serialVersionUID = -1227589126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportSection reportSection = new QReportSection("reportSection");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath comments = createString("comments");

    public final QReportSectionId id;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath sectionDescTxt = createString("sectionDescTxt");

    public final StringPath statusCd = createString("statusCd");

    public QReportSection(String variable) {
        this(ReportSection.class, forVariable(variable), INITS);
    }

    public QReportSection(Path<? extends ReportSection> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportSection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportSection(PathMetadata metadata, PathInits inits) {
        this(ReportSection.class, metadata, inits);
    }

    public QReportSection(Class<? extends ReportSection> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QReportSectionId(forProperty("id")) : null;
    }

}

