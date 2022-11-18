package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoincCode is a Querydsl query type for LoincCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoincCode extends EntityPathBase<LoincCode> {

    private static final long serialVersionUID = -1300487590L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoincCode loincCode = new QLoincCode("loincCode");

    public final StringPath componentName = createString("componentName");

    public final StringPath displayNm = createString("displayNm");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final SetPath<LabtestLoinc, QLabtestLoinc> labtestLoincs = this.<LabtestLoinc, QLabtestLoinc>createSet("labtestLoincs", LabtestLoinc.class, QLabtestLoinc.class, PathInits.DIRECT2);

    public final QLoincCondition loincCondition;

    public final SetPath<LoincSnomedCondition, QLoincSnomedCondition> loincSnomedConditions = this.<LoincSnomedCondition, QLoincSnomedCondition>createSet("loincSnomedConditions", LoincSnomedCondition.class, QLoincSnomedCondition.class, PathInits.DIRECT2);

    public final StringPath methodType = createString("methodType");

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public final ComparablePath<Character> paDerivationExcludeCd = createComparable("paDerivationExcludeCd", Character.class);

    public final StringPath property = createString("property");

    public final StringPath relatedClassCd = createString("relatedClassCd");

    public final StringPath scaleType = createString("scaleType");

    public final StringPath systemCd = createString("systemCd");

    public final StringPath timeAspect = createString("timeAspect");

    public QLoincCode(String variable) {
        this(LoincCode.class, forVariable(variable), INITS);
    }

    public QLoincCode(Path<? extends LoincCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoincCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoincCode(PathMetadata metadata, PathInits inits) {
        this(LoincCode.class, metadata, inits);
    }

    public QLoincCode(Class<? extends LoincCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.loincCondition = inits.isInitialized("loincCondition") ? new QLoincCondition(forProperty("loincCondition"), inits.get("loincCondition")) : null;
    }

}

