package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFilterValue is a Querydsl query type for FilterValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFilterValue extends EntityPathBase<FilterValue> {

    private static final long serialVersionUID = 1601577474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFilterValue filterValue = new QFilterValue("filterValue");

    public final NumberPath<Long> columnUid = createNumber("columnUid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath operator = createString("operator");

    public final QReportFilter reportFilterUid;

    public final NumberPath<Short> sequenceNbr = createNumber("sequenceNbr", Short.class);

    public final StringPath valueTxt = createString("valueTxt");

    public final StringPath valueType = createString("valueType");

    public QFilterValue(String variable) {
        this(FilterValue.class, forVariable(variable), INITS);
    }

    public QFilterValue(Path<? extends FilterValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFilterValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFilterValue(PathMetadata metadata, PathInits inits) {
        this(FilterValue.class, metadata, inits);
    }

    public QFilterValue(Class<? extends FilterValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reportFilterUid = inits.isInitialized("reportFilterUid") ? new QReportFilter(forProperty("reportFilterUid"), inits.get("reportFilterUid")) : null;
    }

}

