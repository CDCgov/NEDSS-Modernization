package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFilterOperator is a Querydsl query type for FilterOperator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFilterOperator extends EntityPathBase<FilterOperator> {

    private static final long serialVersionUID = 712540787L;

    public static final QFilterOperator filterOperator = new QFilterOperator("filterOperator");

    public final StringPath filterOperatorCode = createString("filterOperatorCode");

    public final StringPath filterOperatorDesc = createString("filterOperatorDesc");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QFilterOperator(String variable) {
        super(FilterOperator.class, forVariable(variable));
    }

    public QFilterOperator(Path<? extends FilterOperator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFilterOperator(PathMetadata metadata) {
        super(FilterOperator.class, metadata);
    }

}

