package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthPermSet is a Querydsl query type for AuthPermSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthPermSet extends EntityPathBase<AuthPermSet> {

    private static final long serialVersionUID = 1115888051L;

    public static final QAuthPermSet authPermSet = new QAuthPermSet("authPermSet");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath permSetDesc = createString("permSetDesc");

    public final StringPath permSetNm = createString("permSetNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> sysDefinedPermSetInd = createComparable("sysDefinedPermSetInd", Character.class);

    public QAuthPermSet(String variable) {
        super(AuthPermSet.class, forVariable(variable));
    }

    public QAuthPermSet(Path<? extends AuthPermSet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthPermSet(PathMetadata metadata) {
        super(AuthPermSet.class, metadata);
    }

}

