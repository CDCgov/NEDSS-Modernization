package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1522295189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserUid = createNumber("addUserUid", Long.class);

    public final StringPath category = createString("category");

    public final QDataSource dataSourceUid;

    public final StringPath descTxt = createString("descTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> filterMode = createComparable("filterMode", Character.class);

    public final QReportId id;

    public final ComparablePath<Character> isModifiableInd = createComparable("isModifiableInd", Character.class);

    public final StringPath location = createString("location");

    public final StringPath orgAccessPermis = createString("orgAccessPermis");

    public final NumberPath<Long> ownerUid = createNumber("ownerUid", Long.class);

    public final StringPath progAreaAccessPermis = createString("progAreaAccessPermis");

    public final StringPath reportTitle = createString("reportTitle");

    public final StringPath reportTypeCode = createString("reportTypeCode");

    public final StringPath sectionCd = createString("sectionCd");

    public final ComparablePath<Character> shared = createComparable("shared", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dataSourceUid = inits.isInitialized("dataSourceUid") ? new QDataSource(forProperty("dataSourceUid")) : null;
        this.id = inits.isInitialized("id") ? new QReportId(forProperty("id")) : null;
    }

}

