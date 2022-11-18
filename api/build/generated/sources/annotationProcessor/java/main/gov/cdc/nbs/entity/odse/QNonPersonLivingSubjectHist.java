package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNonPersonLivingSubjectHist is a Querydsl query type for NonPersonLivingSubjectHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNonPersonLivingSubjectHist extends EntityPathBase<NonPersonLivingSubjectHist> {

    private static final long serialVersionUID = 1569291098L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNonPersonLivingSubjectHist nonPersonLivingSubjectHist = new QNonPersonLivingSubjectHist("nonPersonLivingSubjectHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Short> birthOrderNbr = createNumber("birthOrderNbr", Short.class);

    public final ComparablePath<Character> birthSexCd = createComparable("birthSexCd", Character.class);

    public final DateTimePath<java.time.Instant> birthTime = createDateTime("birthTime", java.time.Instant.class);

    public final StringPath breedCd = createString("breedCd");

    public final StringPath breedDescTxt = createString("breedDescTxt");

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final ComparablePath<Character> deceasedIndCd = createComparable("deceasedIndCd", Character.class);

    public final DateTimePath<java.time.Instant> deceasedTime = createDateTime("deceasedTime", java.time.Instant.class);

    public final StringPath description = createString("description");

    public final QNonPersonLivingSubjectHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final ComparablePath<Character> multipleBirthInd = createComparable("multipleBirthInd", Character.class);

    public final StringPath nm = createString("nm");

    public final QNonPersonLivingSubject nonPersonUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath taxonomicClassificationCd = createString("taxonomicClassificationCd");

    public final StringPath taxonomicClassificationDesc = createString("taxonomicClassificationDesc");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QNonPersonLivingSubjectHist(String variable) {
        this(NonPersonLivingSubjectHist.class, forVariable(variable), INITS);
    }

    public QNonPersonLivingSubjectHist(Path<? extends NonPersonLivingSubjectHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNonPersonLivingSubjectHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNonPersonLivingSubjectHist(PathMetadata metadata, PathInits inits) {
        this(NonPersonLivingSubjectHist.class, metadata, inits);
    }

    public QNonPersonLivingSubjectHist(Class<? extends NonPersonLivingSubjectHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QNonPersonLivingSubjectHistId(forProperty("id")) : null;
        this.nonPersonUid = inits.isInitialized("nonPersonUid") ? new QNonPersonLivingSubject(forProperty("nonPersonUid"), inits.get("nonPersonUid")) : null;
    }

}

