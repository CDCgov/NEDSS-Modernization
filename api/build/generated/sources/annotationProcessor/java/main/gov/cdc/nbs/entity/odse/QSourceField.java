package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSourceField is a Querydsl query type for SourceField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSourceField extends EntityPathBase<SourceField> {

    private static final long serialVersionUID = -1902640440L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSourceField sourceField = new QSourceField("sourceField");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNbsMetadataRule nbsMetadataRuleUid;

    public final QRuleInstance ruleInstanceUid;

    public final NumberPath<Integer> sourceFieldSeqNbr = createNumber("sourceFieldSeqNbr", Integer.class);

    public QSourceField(String variable) {
        this(SourceField.class, forVariable(variable), INITS);
    }

    public QSourceField(Path<? extends SourceField> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSourceField(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSourceField(PathMetadata metadata, PathInits inits) {
        this(SourceField.class, metadata, inits);
    }

    public QSourceField(Class<? extends SourceField> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsMetadataRuleUid = inits.isInitialized("nbsMetadataRuleUid") ? new QNbsMetadataRule(forProperty("nbsMetadataRuleUid"), inits.get("nbsMetadataRuleUid")) : null;
        this.ruleInstanceUid = inits.isInitialized("ruleInstanceUid") ? new QRuleInstance(forProperty("ruleInstanceUid"), inits.get("ruleInstanceUid")) : null;
    }

}

