package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnomedCondition is a Querydsl query type for SnomedCondition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnomedCondition extends EntityPathBase<SnomedCondition> {

    private static final long serialVersionUID = 1672713461L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnomedCondition snomedCondition = new QSnomedCondition("snomedCondition");

    public final StringPath diseaseNm = createString("diseaseNm");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QSnomedConditionId id;

    public final StringPath organismSetNm = createString("organismSetNm");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QSnomedCondition(String variable) {
        this(SnomedCondition.class, forVariable(variable), INITS);
    }

    public QSnomedCondition(Path<? extends SnomedCondition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnomedCondition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnomedCondition(PathMetadata metadata, PathInits inits) {
        this(SnomedCondition.class, metadata, inits);
    }

    public QSnomedCondition(Class<? extends SnomedCondition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSnomedConditionId(forProperty("id"), inits.get("id")) : null;
    }

}

