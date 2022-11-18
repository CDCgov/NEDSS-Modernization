package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnomedConditionId is a Querydsl query type for SnomedConditionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSnomedConditionId extends BeanPath<SnomedConditionId> {

    private static final long serialVersionUID = 1159869680L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnomedConditionId snomedConditionId = new QSnomedConditionId("snomedConditionId");

    public final QConditionCode conditionCd;

    public final QSnomedCode snomedCd;

    public QSnomedConditionId(String variable) {
        this(SnomedConditionId.class, forVariable(variable), INITS);
    }

    public QSnomedConditionId(Path<? extends SnomedConditionId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnomedConditionId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnomedConditionId(PathMetadata metadata, PathInits inits) {
        this(SnomedConditionId.class, metadata, inits);
    }

    public QSnomedConditionId(Class<? extends SnomedConditionId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conditionCd = inits.isInitialized("conditionCd") ? new QConditionCode(forProperty("conditionCd"), inits.get("conditionCd")) : null;
        this.snomedCd = inits.isInitialized("snomedCd") ? new QSnomedCode(forProperty("snomedCd")) : null;
    }

}

