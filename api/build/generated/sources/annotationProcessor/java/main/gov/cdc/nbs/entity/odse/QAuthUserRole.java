package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthUserRole is a Querydsl query type for AuthUserRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthUserRole extends EntityPathBase<AuthUserRole> {

    private static final long serialVersionUID = -470523744L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthUserRole authUserRole = new QAuthUserRole("authUserRole");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QAuthPermSet authPermSetUid;

    public final StringPath authRoleNm = createString("authRoleNm");

    public final QAuthUser authUserUid;

    public final NumberPath<Integer> dispSeqNbr = createNumber("dispSeqNbr", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath progAreaCd = createString("progAreaCd");

    public final ComparablePath<Character> readOnlyInd = createComparable("readOnlyInd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> roleGuestInd = createComparable("roleGuestInd", Character.class);

    public QAuthUserRole(String variable) {
        this(AuthUserRole.class, forVariable(variable), INITS);
    }

    public QAuthUserRole(Path<? extends AuthUserRole> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthUserRole(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthUserRole(PathMetadata metadata, PathInits inits) {
        this(AuthUserRole.class, metadata, inits);
    }

    public QAuthUserRole(Class<? extends AuthUserRole> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authPermSetUid = inits.isInitialized("authPermSetUid") ? new QAuthPermSet(forProperty("authPermSetUid")) : null;
        this.authUserUid = inits.isInitialized("authUserUid") ? new QAuthUser(forProperty("authUserUid"), inits.get("authUserUid")) : null;
    }

}

