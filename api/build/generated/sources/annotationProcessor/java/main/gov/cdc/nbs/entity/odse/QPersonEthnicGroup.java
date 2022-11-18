package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonEthnicGroup is a Querydsl query type for PersonEthnicGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonEthnicGroup extends EntityPathBase<PersonEthnicGroup> {

    private static final long serialVersionUID = 256570820L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonEthnicGroup personEthnicGroup = new QPersonEthnicGroup("personEthnicGroup");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath ethnicGroupDescTxt = createString("ethnicGroupDescTxt");

    public final QPersonEthnicGroupId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QPerson personUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonEthnicGroup(String variable) {
        this(PersonEthnicGroup.class, forVariable(variable), INITS);
    }

    public QPersonEthnicGroup(Path<? extends PersonEthnicGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonEthnicGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonEthnicGroup(PathMetadata metadata, PathInits inits) {
        this(PersonEthnicGroup.class, metadata, inits);
    }

    public QPersonEthnicGroup(Class<? extends PersonEthnicGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonEthnicGroupId(forProperty("id")) : null;
        this.personUid = inits.isInitialized("personUid") ? new QPerson(forProperty("personUid"), inits.get("personUid")) : null;
    }

}

