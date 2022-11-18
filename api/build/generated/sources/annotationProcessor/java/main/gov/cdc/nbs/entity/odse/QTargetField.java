package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTargetField is a Querydsl query type for TargetField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTargetField extends EntityPathBase<TargetField> {

    private static final long serialVersionUID = 392393490L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTargetField targetField = new QTargetField("targetField");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNbsMetadataRule nbsMetadataRuleUid;

    public final QRuleInstance ruleInstanceUid;

    public QTargetField(String variable) {
        this(TargetField.class, forVariable(variable), INITS);
    }

    public QTargetField(Path<? extends TargetField> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTargetField(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTargetField(PathMetadata metadata, PathInits inits) {
        this(TargetField.class, metadata, inits);
    }

    public QTargetField(Class<? extends TargetField> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsMetadataRuleUid = inits.isInitialized("nbsMetadataRuleUid") ? new QNbsMetadataRule(forProperty("nbsMetadataRuleUid"), inits.get("nbsMetadataRuleUid")) : null;
        this.ruleInstanceUid = inits.isInitialized("ruleInstanceUid") ? new QRuleInstance(forProperty("ruleInstanceUid"), inits.get("ruleInstanceUid")) : null;
    }

}

