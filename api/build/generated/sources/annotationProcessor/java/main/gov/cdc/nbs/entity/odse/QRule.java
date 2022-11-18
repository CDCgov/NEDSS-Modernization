package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = -99892749L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRule rule = new QRule("rule");

    public final StringPath comments = createString("comments");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ruleName = createString("ruleName");

    public final QRuleType ruleTypeUid;

    public QRule(String variable) {
        this(Rule.class, forVariable(variable), INITS);
    }

    public QRule(Path<? extends Rule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRule(PathMetadata metadata, PathInits inits) {
        this(Rule.class, metadata, inits);
    }

    public QRule(Class<? extends Rule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ruleTypeUid = inits.isInitialized("ruleTypeUid") ? new QRuleType(forProperty("ruleTypeUid")) : null;
    }

}

