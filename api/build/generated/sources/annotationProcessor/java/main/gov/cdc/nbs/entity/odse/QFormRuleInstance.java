package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFormRuleInstance is a Querydsl query type for FormRuleInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFormRuleInstance extends EntityPathBase<FormRuleInstance> {

    private static final long serialVersionUID = -1216700660L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFormRuleInstance formRuleInstance = new QFormRuleInstance("formRuleInstance");

    public final StringPath formCode = createString("formCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final QRuleInstance ruleInstanceUid;

    public QFormRuleInstance(String variable) {
        this(FormRuleInstance.class, forVariable(variable), INITS);
    }

    public QFormRuleInstance(Path<? extends FormRuleInstance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFormRuleInstance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFormRuleInstance(PathMetadata metadata, PathInits inits) {
        this(FormRuleInstance.class, metadata, inits);
    }

    public QFormRuleInstance(Class<? extends FormRuleInstance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ruleInstanceUid = inits.isInitialized("ruleInstanceUid") ? new QRuleInstance(forProperty("ruleInstanceUid"), inits.get("ruleInstanceUid")) : null;
    }

}

