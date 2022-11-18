package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSourceValue is a Querydsl query type for SourceValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSourceValue extends EntityPathBase<SourceValue> {

    private static final long serialVersionUID = -1888095425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSourceValue sourceValue1 = new QSourceValue("sourceValue1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOperatorType operatorTypeUid;

    public final QSourceField sourceFieldUid;

    public final StringPath sourceValue = createString("sourceValue");

    public QSourceValue(String variable) {
        this(SourceValue.class, forVariable(variable), INITS);
    }

    public QSourceValue(Path<? extends SourceValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSourceValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSourceValue(PathMetadata metadata, PathInits inits) {
        this(SourceValue.class, metadata, inits);
    }

    public QSourceValue(Class<? extends SourceValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.operatorTypeUid = inits.isInitialized("operatorTypeUid") ? new QOperatorType(forProperty("operatorTypeUid")) : null;
        this.sourceFieldUid = inits.isInitialized("sourceFieldUid") ? new QSourceField(forProperty("sourceFieldUid"), inits.get("sourceFieldUid")) : null;
    }

}

