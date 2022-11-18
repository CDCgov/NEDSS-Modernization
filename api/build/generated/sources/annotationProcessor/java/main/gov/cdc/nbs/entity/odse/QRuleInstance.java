package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRuleInstance is a Querydsl query type for RuleInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRuleInstance extends EntityPathBase<RuleInstance> {

    private static final long serialVersionUID = 950081448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRuleInstance ruleInstance = new QRuleInstance("ruleInstance");

    public final StringPath comments = createString("comments");

    public final QConsequenceIndicator conseqIndUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRule ruleUid;

    public QRuleInstance(String variable) {
        this(RuleInstance.class, forVariable(variable), INITS);
    }

    public QRuleInstance(Path<? extends RuleInstance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRuleInstance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRuleInstance(PathMetadata metadata, PathInits inits) {
        this(RuleInstance.class, metadata, inits);
    }

    public QRuleInstance(Class<? extends RuleInstance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conseqIndUid = inits.isInitialized("conseqIndUid") ? new QConsequenceIndicator(forProperty("conseqIndUid")) : null;
        this.ruleUid = inits.isInitialized("ruleUid") ? new QRule(forProperty("ruleUid"), inits.get("ruleUid")) : null;
    }

}

