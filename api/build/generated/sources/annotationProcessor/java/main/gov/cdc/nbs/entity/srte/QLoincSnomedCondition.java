package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoincSnomedCondition is a Querydsl query type for LoincSnomedCondition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoincSnomedCondition extends EntityPathBase<LoincSnomedCondition> {

    private static final long serialVersionUID = 477909398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoincSnomedCondition loincSnomedCondition = new QLoincSnomedCondition("loincSnomedCondition");

    public final QConditionCode conditionCd;

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLoincCode loincCd;

    public final QSnomedCode snomedCd;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QLoincSnomedCondition(String variable) {
        this(LoincSnomedCondition.class, forVariable(variable), INITS);
    }

    public QLoincSnomedCondition(Path<? extends LoincSnomedCondition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoincSnomedCondition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoincSnomedCondition(PathMetadata metadata, PathInits inits) {
        this(LoincSnomedCondition.class, metadata, inits);
    }

    public QLoincSnomedCondition(Class<? extends LoincSnomedCondition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conditionCd = inits.isInitialized("conditionCd") ? new QConditionCode(forProperty("conditionCd"), inits.get("conditionCd")) : null;
        this.loincCd = inits.isInitialized("loincCd") ? new QLoincCode(forProperty("loincCd"), inits.get("loincCd")) : null;
        this.snomedCd = inits.isInitialized("snomedCd") ? new QSnomedCode(forProperty("snomedCd")) : null;
    }

}

