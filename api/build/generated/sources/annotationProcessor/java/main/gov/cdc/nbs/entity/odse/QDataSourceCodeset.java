package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataSourceCodeset is a Querydsl query type for DataSourceCodeset
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataSourceCodeset extends EntityPathBase<DataSourceCodeset> {

    private static final long serialVersionUID = 913750425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataSourceCodeset dataSourceCodeset = new QDataSourceCodeset("dataSourceCodeset");

    public final StringPath codeDescCd = createString("codeDescCd");

    public final StringPath codesetNm = createString("codesetNm");

    public final QDataSourceColumn columnUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDataSourceCodeset(String variable) {
        this(DataSourceCodeset.class, forVariable(variable), INITS);
    }

    public QDataSourceCodeset(Path<? extends DataSourceCodeset> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataSourceCodeset(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataSourceCodeset(PathMetadata metadata, PathInits inits) {
        this(DataSourceCodeset.class, metadata, inits);
    }

    public QDataSourceCodeset(Class<? extends DataSourceCodeset> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.columnUid = inits.isInitialized("columnUid") ? new QDataSourceColumn(forProperty("columnUid"), inits.get("columnUid")) : null;
    }

}

