package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonRace is a Querydsl query type for PersonRace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonRace extends EntityPathBase<PersonRace> {

    private static final long serialVersionUID = -1180402307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonRace personRace = new QPersonRace("personRace");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final QPersonRaceId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final QPerson personUid;

    public final EnumPath<gov.cdc.nbs.entity.enums.Race> raceCategoryCd = createEnum("raceCategoryCd", gov.cdc.nbs.entity.enums.Race.class);

    public final StringPath raceDescTxt = createString("raceDescTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonRace(String variable) {
        this(PersonRace.class, forVariable(variable), INITS);
    }

    public QPersonRace(Path<? extends PersonRace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonRace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonRace(PathMetadata metadata, PathInits inits) {
        this(PersonRace.class, metadata, inits);
    }

    public QPersonRace(Class<? extends PersonRace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonRaceId(forProperty("id")) : null;
        this.personUid = inits.isInitialized("personUid") ? new QPerson(forProperty("personUid"), inits.get("personUid")) : null;
    }

}

