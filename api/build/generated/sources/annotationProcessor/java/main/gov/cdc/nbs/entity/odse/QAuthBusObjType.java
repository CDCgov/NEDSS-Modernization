package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthBusObjType is a Querydsl query type for AuthBusObjType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthBusObjType extends EntityPathBase<AuthBusObjType> {

    private static final long serialVersionUID = 1296502832L;

    public static final QAuthBusObjType authBusObjType = new QAuthBusObjType("authBusObjType");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath busObjDispNm = createString("busObjDispNm");

    public final StringPath busObjNm = createString("busObjNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> jurisdictionInd = createComparable("jurisdictionInd", Character.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Integer> operationSequence = createNumber("operationSequence", Integer.class);

    public final ComparablePath<Character> progAreaInd = createComparable("progAreaInd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QAuthBusObjType(String variable) {
        super(AuthBusObjType.class, forVariable(variable));
    }

    public QAuthBusObjType(Path<? extends AuthBusObjType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthBusObjType(PathMetadata metadata) {
        super(AuthBusObjType.class, metadata);
    }

}

