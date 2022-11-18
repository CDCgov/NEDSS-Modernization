package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonEthnicGroupHist is a Querydsl query type for PersonEthnicGroupHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonEthnicGroupHist extends EntityPathBase<PersonEthnicGroupHist> {

    private static final long serialVersionUID = -508246266L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonEthnicGroupHist personEthnicGroupHist = new QPersonEthnicGroupHist("personEthnicGroupHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath ethnicGroupDescTxt = createString("ethnicGroupDescTxt");

    public final QPersonEthnicGroupHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonEthnicGroupHist(String variable) {
        this(PersonEthnicGroupHist.class, forVariable(variable), INITS);
    }

    public QPersonEthnicGroupHist(Path<? extends PersonEthnicGroupHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonEthnicGroupHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonEthnicGroupHist(PathMetadata metadata, PathInits inits) {
        this(PersonEthnicGroupHist.class, metadata, inits);
    }

    public QPersonEthnicGroupHist(Class<? extends PersonEthnicGroupHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonEthnicGroupHistId(forProperty("id")) : null;
    }

}

