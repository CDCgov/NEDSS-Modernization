package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonNameHist is a Querydsl query type for PersonNameHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonNameHist extends EntityPathBase<PersonNameHist> {

    private static final long serialVersionUID = -1582461703L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonNameHist personNameHist = new QPersonNameHist("personNameHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> asOfDate = createDateTime("asOfDate", java.time.Instant.class);

    public final ComparablePath<Character> defaultNmInd = createComparable("defaultNmInd", Character.class);

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final StringPath firstNm = createString("firstNm");

    public final StringPath firstNmSndx = createString("firstNmSndx");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QPersonNameHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath lastNm = createString("lastNm");

    public final StringPath lastNm2 = createString("lastNm2");

    public final StringPath lastNm2Sndx = createString("lastNm2Sndx");

    public final StringPath lastNmSndx = createString("lastNmSndx");

    public final StringPath middleNm = createString("middleNm");

    public final StringPath middleNm2 = createString("middleNm2");

    public final StringPath nmDegree = createString("nmDegree");

    public final StringPath nmPrefix = createString("nmPrefix");

    public final StringPath nmSuffix = createString("nmSuffix");

    public final StringPath nmUseCd = createString("nmUseCd");

    public final QPersonName personName;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonNameHist(String variable) {
        this(PersonNameHist.class, forVariable(variable), INITS);
    }

    public QPersonNameHist(Path<? extends PersonNameHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonNameHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonNameHist(PathMetadata metadata, PathInits inits) {
        this(PersonNameHist.class, metadata, inits);
    }

    public QPersonNameHist(Class<? extends PersonNameHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonNameHistId(forProperty("id")) : null;
        this.personName = inits.isInitialized("personName") ? new QPersonName(forProperty("personName"), inits.get("personName")) : null;
    }

}

