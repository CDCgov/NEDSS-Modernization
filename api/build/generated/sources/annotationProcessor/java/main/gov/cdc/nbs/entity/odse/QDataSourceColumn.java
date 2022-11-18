package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataSourceColumn is a Querydsl query type for DataSourceColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataSourceColumn extends EntityPathBase<DataSourceColumn> {

    private static final long serialVersionUID = -1494291310L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataSourceColumn dataSourceColumn = new QDataSourceColumn("dataSourceColumn");

    public final NumberPath<Integer> columnMaxLen = createNumber("columnMaxLen", Integer.class);

    public final StringPath columnName = createString("columnName");

    public final StringPath columnTitle = createString("columnTitle");

    public final StringPath columnTypeCode = createString("columnTypeCode");

    public final QDataSource dataSourceUid;

    public final StringPath descTxt = createString("descTxt");

    public final ComparablePath<Character> displayable = createComparable("displayable", Character.class);

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> filterable = createComparable("filterable", Character.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDataSourceColumn(String variable) {
        this(DataSourceColumn.class, forVariable(variable), INITS);
    }

    public QDataSourceColumn(Path<? extends DataSourceColumn> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataSourceColumn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataSourceColumn(PathMetadata metadata, PathInits inits) {
        this(DataSourceColumn.class, metadata, inits);
    }

    public QDataSourceColumn(Class<? extends DataSourceColumn> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dataSourceUid = inits.isInitialized("dataSourceUid") ? new QDataSource(forProperty("dataSourceUid")) : null;
    }

}

