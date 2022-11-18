package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabResult is a Querydsl query type for LabResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabResult extends EntityPathBase<LabResult> {

    private static final long serialVersionUID = 1649047132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabResult labResult = new QLabResult("labResult");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final QConditionCode defaultConditionCd;

    public final QProgramAreaCode defaultProgAreaCd;

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QLabResultId id;

    public final QLabCodingSystem laboratory;

    public final StringPath labResultDescTxt = createString("labResultDescTxt");

    public final SetPath<LabResultSnomed, QLabResultSnomed> labResultSnomeds = this.<LabResultSnomed, QLabResultSnomed>createSet("labResultSnomeds", LabResultSnomed.class, QLabResultSnomed.class, PathInits.DIRECT2);

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public final ComparablePath<Character> organismNameInd = createComparable("organismNameInd", Character.class);

    public final ComparablePath<Character> paDerivationExcludeCd = createComparable("paDerivationExcludeCd", Character.class);

    public QLabResult(String variable) {
        this(LabResult.class, forVariable(variable), INITS);
    }

    public QLabResult(Path<? extends LabResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabResult(PathMetadata metadata, PathInits inits) {
        this(LabResult.class, metadata, inits);
    }

    public QLabResult(Class<? extends LabResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.defaultConditionCd = inits.isInitialized("defaultConditionCd") ? new QConditionCode(forProperty("defaultConditionCd"), inits.get("defaultConditionCd")) : null;
        this.defaultProgAreaCd = inits.isInitialized("defaultProgAreaCd") ? new QProgramAreaCode(forProperty("defaultProgAreaCd")) : null;
        this.id = inits.isInitialized("id") ? new QLabResultId(forProperty("id")) : null;
        this.laboratory = inits.isInitialized("laboratory") ? new QLabCodingSystem(forProperty("laboratory")) : null;
    }

}

