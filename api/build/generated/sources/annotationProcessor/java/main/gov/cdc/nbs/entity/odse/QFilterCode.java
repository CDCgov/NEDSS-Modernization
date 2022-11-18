package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFilterCode is a Querydsl query type for FilterCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFilterCode extends EntityPathBase<FilterCode> {

    private static final long serialVersionUID = 882394940L;

    public static final QFilterCode filterCode1 = new QFilterCode("filterCode1");

    public final StringPath codeTable = createString("codeTable");

    public final StringPath descTxt = createString("descTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath filterCode = createString("filterCode");

    public final StringPath filterCodeSetNm = createString("filterCodeSetNm");

    public final StringPath filterName = createString("filterName");

    public final StringPath filterType = createString("filterType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QFilterCode(String variable) {
        super(FilterCode.class, forVariable(variable));
    }

    public QFilterCode(Path<? extends FilterCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFilterCode(PathMetadata metadata) {
        super(FilterCode.class, metadata);
    }

}

