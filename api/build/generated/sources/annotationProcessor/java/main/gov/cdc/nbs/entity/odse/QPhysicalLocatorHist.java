package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPhysicalLocatorHist is a Querydsl query type for PhysicalLocatorHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhysicalLocatorHist extends EntityPathBase<PhysicalLocatorHist> {

    private static final long serialVersionUID = -1657583702L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPhysicalLocatorHist physicalLocatorHist = new QPhysicalLocatorHist("physicalLocatorHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final QPhysicalLocatorHistId id;

    public final ArrayPath<byte[], Byte> imageTxt = createArray("imageTxt", byte[].class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath locatorTxt = createString("locatorTxt");

    public final QPhysicalLocator physicalLocatorUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPhysicalLocatorHist(String variable) {
        this(PhysicalLocatorHist.class, forVariable(variable), INITS);
    }

    public QPhysicalLocatorHist(Path<? extends PhysicalLocatorHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPhysicalLocatorHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPhysicalLocatorHist(PathMetadata metadata, PathInits inits) {
        this(PhysicalLocatorHist.class, metadata, inits);
    }

    public QPhysicalLocatorHist(Class<? extends PhysicalLocatorHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPhysicalLocatorHistId(forProperty("id")) : null;
        this.physicalLocatorUid = inits.isInitialized("physicalLocatorUid") ? new QPhysicalLocator(forProperty("physicalLocatorUid")) : null;
    }

}

