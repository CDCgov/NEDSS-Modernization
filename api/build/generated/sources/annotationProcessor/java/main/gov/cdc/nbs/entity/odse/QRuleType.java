package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRuleType is a Querydsl query type for RuleType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRuleType extends EntityPathBase<RuleType> {

    private static final long serialVersionUID = -1446276147L;

    public static final QRuleType ruleType = new QRuleType("ruleType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> ruleTypeCode = createComparable("ruleTypeCode", Character.class);

    public final StringPath ruleTypeDescription = createString("ruleTypeDescription");

    public QRuleType(String variable) {
        super(RuleType.class, forVariable(variable));
    }

    public QRuleType(Path<? extends RuleType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRuleType(PathMetadata metadata) {
        super(RuleType.class, metadata);
    }

}

