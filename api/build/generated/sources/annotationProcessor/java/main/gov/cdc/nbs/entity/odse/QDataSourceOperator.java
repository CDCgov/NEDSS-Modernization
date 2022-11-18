package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataSourceOperator is a Querydsl query type for DataSourceOperator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataSourceOperator extends EntityPathBase<DataSourceOperator> {

    private static final long serialVersionUID = -1372003616L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataSourceOperator dataSourceOperator = new QDataSourceOperator("dataSourceOperator");

    public final StringPath columnTypeCode = createString("columnTypeCode");

    public final QFilterOperator filterOperatorUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDataSourceOperator(String variable) {
        this(DataSourceOperator.class, forVariable(variable), INITS);
    }

    public QDataSourceOperator(Path<? extends DataSourceOperator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataSourceOperator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataSourceOperator(PathMetadata metadata, PathInits inits) {
        this(DataSourceOperator.class, metadata, inits);
    }

    public QDataSourceOperator(Class<? extends DataSourceOperator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.filterOperatorUid = inits.isInitialized("filterOperatorUid") ? new QFilterOperator(forProperty("filterOperatorUid")) : null;
    }

}

