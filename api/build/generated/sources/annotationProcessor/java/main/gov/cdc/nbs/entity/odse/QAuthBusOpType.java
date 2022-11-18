package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthBusOpType is a Querydsl query type for AuthBusOpType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthBusOpType extends EntityPathBase<AuthBusOpType> {

    private static final long serialVersionUID = 331226492L;

    public static final QAuthBusOpType authBusOpType = new QAuthBusOpType("authBusOpType");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath busOpDispNm = createString("busOpDispNm");

    public final StringPath busOpNm = createString("busOpNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Integer> operationSequence = createNumber("operationSequence", Integer.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QAuthBusOpType(String variable) {
        super(AuthBusOpType.class, forVariable(variable));
    }

    public QAuthBusOpType(Path<? extends AuthBusOpType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthBusOpType(PathMetadata metadata) {
        super(AuthBusOpType.class, metadata);
    }

}

