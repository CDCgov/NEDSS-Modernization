package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTargetValue is a Querydsl query type for TargetValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTargetValue extends EntityPathBase<TargetValue> {

    private static final long serialVersionUID = 406938505L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTargetValue targetValue1 = new QTargetValue("targetValue1");

    public final QConsequenceIndicator conseqIndUid;

    public final QErrorMessage errorMessageUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOperatorType operatorTypeUid;

    public final QTargetField targetFieldUid;

    public final StringPath targetValue = createString("targetValue");

    public QTargetValue(String variable) {
        this(TargetValue.class, forVariable(variable), INITS);
    }

    public QTargetValue(Path<? extends TargetValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTargetValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTargetValue(PathMetadata metadata, PathInits inits) {
        this(TargetValue.class, metadata, inits);
    }

    public QTargetValue(Class<? extends TargetValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conseqIndUid = inits.isInitialized("conseqIndUid") ? new QConsequenceIndicator(forProperty("conseqIndUid")) : null;
        this.errorMessageUid = inits.isInitialized("errorMessageUid") ? new QErrorMessage(forProperty("errorMessageUid")) : null;
        this.operatorTypeUid = inits.isInitialized("operatorTypeUid") ? new QOperatorType(forProperty("operatorTypeUid")) : null;
        this.targetFieldUid = inits.isInitialized("targetFieldUid") ? new QTargetField(forProperty("targetFieldUid"), inits.get("targetFieldUid")) : null;
    }

}

