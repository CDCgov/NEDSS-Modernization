package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonRaceHist is a Querydsl query type for PersonRaceHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonRaceHist extends EntityPathBase<PersonRaceHist> {

    private static final long serialVersionUID = 807520831L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonRaceHist personRaceHist = new QPersonRaceHist("personRaceHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final QPersonRaceHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath raceCategoryCd = createString("raceCategoryCd");

    public final StringPath raceDescTxt = createString("raceDescTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonRaceHist(String variable) {
        this(PersonRaceHist.class, forVariable(variable), INITS);
    }

    public QPersonRaceHist(Path<? extends PersonRaceHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonRaceHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonRaceHist(PathMetadata metadata, PathInits inits) {
        this(PersonRaceHist.class, metadata, inits);
    }

    public QPersonRaceHist(Class<? extends PersonRaceHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonRaceHistId(forProperty("id")) : null;
    }

}

