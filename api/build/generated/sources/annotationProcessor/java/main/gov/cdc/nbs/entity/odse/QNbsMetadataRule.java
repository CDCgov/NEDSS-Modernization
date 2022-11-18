package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsMetadataRule is a Querydsl query type for NbsMetadataRule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsMetadataRule extends EntityPathBase<NbsMetadataRule> {

    private static final long serialVersionUID = -964849069L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsMetadataRule nbsMetadataRule = new QNbsMetadataRule("nbsMetadataRule");

    public final StringPath componentIdentifier = createString("componentIdentifier");

    public final StringPath componentType = createString("componentType");

    public final QNbsQuestion componentUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QNbsMetadataRule(String variable) {
        this(NbsMetadataRule.class, forVariable(variable), INITS);
    }

    public QNbsMetadataRule(Path<? extends NbsMetadataRule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsMetadataRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsMetadataRule(PathMetadata metadata, PathInits inits) {
        this(NbsMetadataRule.class, metadata, inits);
    }

    public QNbsMetadataRule(Class<? extends NbsMetadataRule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.componentUid = inits.isInitialized("componentUid") ? new QNbsQuestion(forProperty("componentUid")) : null;
    }

}

