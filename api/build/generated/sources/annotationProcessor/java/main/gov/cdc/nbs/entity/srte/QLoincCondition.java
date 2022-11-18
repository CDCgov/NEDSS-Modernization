package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoincCondition is a Querydsl query type for LoincCondition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoincCondition extends EntityPathBase<LoincCondition> {

    private static final long serialVersionUID = 782546126L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoincCondition loincCondition = new QLoincCondition("loincCondition");

    public final QConditionCode conditionCd;

    public final StringPath diseaseNm = createString("diseaseNm");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final QLoincCode loincCode;

    public final StringPath reportedNumericValue = createString("reportedNumericValue");

    public final StringPath reportedValue = createString("reportedValue");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QLoincCondition(String variable) {
        this(LoincCondition.class, forVariable(variable), INITS);
    }

    public QLoincCondition(Path<? extends LoincCondition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoincCondition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoincCondition(PathMetadata metadata, PathInits inits) {
        this(LoincCondition.class, metadata, inits);
    }

    public QLoincCondition(Class<? extends LoincCondition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conditionCd = inits.isInitialized("conditionCd") ? new QConditionCode(forProperty("conditionCd"), inits.get("conditionCd")) : null;
        this.loincCode = inits.isInitialized("loincCode") ? new QLoincCode(forProperty("loincCode"), inits.get("loincCode")) : null;
    }

}

