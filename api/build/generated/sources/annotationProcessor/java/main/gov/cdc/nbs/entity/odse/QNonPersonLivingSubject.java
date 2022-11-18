package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNonPersonLivingSubject is a Querydsl query type for NonPersonLivingSubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNonPersonLivingSubject extends EntityPathBase<NonPersonLivingSubject> {

    private static final long serialVersionUID = -189860840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNonPersonLivingSubject nonPersonLivingSubject = new QNonPersonLivingSubject("nonPersonLivingSubject");

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

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final ComparablePath<Character> multipleBirthInd = createComparable("multipleBirthInd", Character.class);

    public final QNBSEntity NBSEntity;

    public final StringPath nm = createString("nm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath taxonomicClassificationCd = createString("taxonomicClassificationCd");

    public final StringPath taxonomicClassificationDesc = createString("taxonomicClassificationDesc");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QNonPersonLivingSubject(String variable) {
        this(NonPersonLivingSubject.class, forVariable(variable), INITS);
    }

    public QNonPersonLivingSubject(Path<? extends NonPersonLivingSubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNonPersonLivingSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNonPersonLivingSubject(PathMetadata metadata, PathInits inits) {
        this(NonPersonLivingSubject.class, metadata, inits);
    }

    public QNonPersonLivingSubject(Class<? extends NonPersonLivingSubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.NBSEntity = inits.isInitialized("NBSEntity") ? new QNBSEntity(forProperty("NBSEntity")) : null;
    }

}

