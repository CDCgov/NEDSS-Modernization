package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDisplayColumn is a Querydsl query type for DisplayColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisplayColumn extends EntityPathBase<DisplayColumn> {

    private static final long serialVersionUID = -286983775L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDisplayColumn displayColumn = new QDisplayColumn("displayColumn");

    public final QDataSourceColumn columnUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QReport report;

    public final NumberPath<Short> sequenceNbr = createNumber("sequenceNbr", Short.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDisplayColumn(String variable) {
        this(DisplayColumn.class, forVariable(variable), INITS);
    }

    public QDisplayColumn(Path<? extends DisplayColumn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDisplayColumn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDisplayColumn(PathMetadata metadata, PathInits inits) {
        this(DisplayColumn.class, metadata, inits);
    }

    public QDisplayColumn(Class<? extends DisplayColumn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.columnUid = inits.isInitialized("columnUid") ? new QDataSourceColumn(forProperty("columnUid"), inits.get("columnUid")) : null;
        this.report = inits.isInitialized("report") ? new QReport(forProperty("report"), inits.get("report")) : null;
    }

}

