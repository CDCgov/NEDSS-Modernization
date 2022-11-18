package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeleLocatorHist is a Querydsl query type for TeleLocatorHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeleLocatorHist extends EntityPathBase<TeleLocatorHist> {

    private static final long serialVersionUID = 1088909175L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeleLocatorHist teleLocatorHist = new QTeleLocatorHist("teleLocatorHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cntryCd = createString("cntryCd");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath extensionTxt = createString("extensionTxt");

    public final QTeleLocatorHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath phoneNbrTxt = createString("phoneNbrTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final QTeleLocator teleLocatorUid;

    public final StringPath urlAddress = createString("urlAddress");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QTeleLocatorHist(String variable) {
        this(TeleLocatorHist.class, forVariable(variable), INITS);
    }

    public QTeleLocatorHist(Path<? extends TeleLocatorHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeleLocatorHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeleLocatorHist(PathMetadata metadata, PathInits inits) {
        this(TeleLocatorHist.class, metadata, inits);
    }

    public QTeleLocatorHist(Class<? extends TeleLocatorHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QTeleLocatorHistId(forProperty("id")) : null;
        this.teleLocatorUid = inits.isInitialized("teleLocatorUid") ? new QTeleLocator(forProperty("teleLocatorUid")) : null;
    }

}

