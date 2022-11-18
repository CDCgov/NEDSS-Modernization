package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthProgAreaAdmin is a Querydsl query type for AuthProgAreaAdmin
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthProgAreaAdmin extends EntityPathBase<AuthProgAreaAdmin> {

    private static final long serialVersionUID = -502163927L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthProgAreaAdmin authProgAreaAdmin = new QAuthProgAreaAdmin("authProgAreaAdmin");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final ComparablePath<Character> authUserInd = createComparable("authUserInd", Character.class);

    public final QAuthUser authUserUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath progAreaCd = createString("progAreaCd");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QAuthProgAreaAdmin(String variable) {
        this(AuthProgAreaAdmin.class, forVariable(variable), INITS);
    }

    public QAuthProgAreaAdmin(Path<? extends AuthProgAreaAdmin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthProgAreaAdmin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthProgAreaAdmin(PathMetadata metadata, PathInits inits) {
        this(AuthProgAreaAdmin.class, metadata, inits);
    }

    public QAuthProgAreaAdmin(Class<? extends AuthProgAreaAdmin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authUserUid = inits.isInitialized("authUserUid") ? new QAuthUser(forProperty("authUserUid"), inits.get("authUserUid")) : null;
    }

}

