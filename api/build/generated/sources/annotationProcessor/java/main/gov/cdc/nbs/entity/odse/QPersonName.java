package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonName is a Querydsl query type for PersonName
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonName extends EntityPathBase<PersonName> {

    private static final long serialVersionUID = -1180521161L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonName personName = new QPersonName("personName");

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

    public final QPersonNameId id;

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

    public final EnumPath<gov.cdc.nbs.graphql.input.PatientInput.Suffix> nmSuffix = createEnum("nmSuffix", gov.cdc.nbs.graphql.input.PatientInput.Suffix.class);

    public final StringPath nmUseCd = createString("nmUseCd");

    public final QPerson personUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPersonName(String variable) {
        this(PersonName.class, forVariable(variable), INITS);
    }

    public QPersonName(Path<? extends PersonName> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonName(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonName(PathMetadata metadata, PathInits inits) {
        this(PersonName.class, metadata, inits);
    }

    public QPersonName(Class<? extends PersonName> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPersonNameId(forProperty("id")) : null;
        this.personUid = inits.isInitialized("personUid") ? new QPerson(forProperty("personUid"), inits.get("personUid")) : null;
    }

}

