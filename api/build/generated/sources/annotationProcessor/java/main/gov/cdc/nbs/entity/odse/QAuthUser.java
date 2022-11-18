package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthUser is a Querydsl query type for AuthUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthUser extends EntityPathBase<AuthUser> {

    private static final long serialVersionUID = -777453302L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthUser authUser = new QAuthUser("authUser");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QOrganization externalOrgUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> jurisdictionDerivationInd = createComparable("jurisdictionDerivationInd", Character.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final ComparablePath<Character> masterSecAdminInd = createComparable("masterSecAdminInd", Character.class);

    public final NumberPath<Long> nedssEntryId = createNumber("nedssEntryId", Long.class);

    public final ComparablePath<Character> progAreaAdminInd = createComparable("progAreaAdminInd", Character.class);

    public final QNBSEntity providerUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userComments = createString("userComments");

    public final StringPath userDepartment = createString("userDepartment");

    public final StringPath userFirstNm = createString("userFirstNm");

    public final StringPath userId = createString("userId");

    public final StringPath userLastNm = createString("userLastNm");

    public final StringPath userMobilePhone = createString("userMobilePhone");

    public final StringPath userPassword = createString("userPassword");

    public final StringPath userTitle = createString("userTitle");

    public final StringPath userType = createString("userType");

    public final StringPath userWorkEmail = createString("userWorkEmail");

    public final StringPath userWorkPhone = createString("userWorkPhone");

    public QAuthUser(String variable) {
        this(AuthUser.class, forVariable(variable), INITS);
    }

    public QAuthUser(Path<? extends AuthUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthUser(PathMetadata metadata, PathInits inits) {
        this(AuthUser.class, metadata, inits);
    }

    public QAuthUser(Class<? extends AuthUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.externalOrgUid = inits.isInitialized("externalOrgUid") ? new QOrganization(forProperty("externalOrgUid"), inits.get("externalOrgUid")) : null;
        this.providerUid = inits.isInitialized("providerUid") ? new QNBSEntity(forProperty("providerUid")) : null;
    }

}

