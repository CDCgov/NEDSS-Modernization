package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleHist is a Querydsl query type for RoleHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoleHist extends EntityPathBase<RoleHist> {

    private static final long serialVersionUID = 1818263599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleHist roleHist = new QRoleHist("roleHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QRoleHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath scopingClassCd = createString("scopingClassCd");

    public final NumberPath<Long> scopingEntityUid = createNumber("scopingEntityUid", Long.class);

    public final StringPath scopingRoleCd = createString("scopingRoleCd");

    public final NumberPath<Short> scopingRoleSeq = createNumber("scopingRoleSeq", Short.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath subjectClassCd = createString("subjectClassCd");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QRoleHist(String variable) {
        this(RoleHist.class, forVariable(variable), INITS);
    }

    public QRoleHist(Path<? extends RoleHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleHist(PathMetadata metadata, PathInits inits) {
        this(RoleHist.class, metadata, inits);
    }

    public QRoleHist(Class<? extends RoleHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QRoleHistId(forProperty("id")) : null;
    }

}

