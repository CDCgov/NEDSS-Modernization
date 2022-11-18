package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaRuleMetadatum is a Querydsl query type for WaRuleMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaRuleMetadatum extends EntityPathBase<WaRuleMetadatum> {

    private static final long serialVersionUID = -477384723L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaRuleMetadatum waRuleMetadatum = new QWaRuleMetadatum("waRuleMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath errMsgTxt = createString("errMsgTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath javascriptFunction = createString("javascriptFunction");

    public final StringPath javascriptFunctionNm = createString("javascriptFunctionNm");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath logic = createString("logic");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath ruleCd = createString("ruleCd");

    public final StringPath ruleDescTxt = createString("ruleDescTxt");

    public final StringPath ruleExpression = createString("ruleExpression");

    public final StringPath sourceQuestionIdentifier = createString("sourceQuestionIdentifier");

    public final StringPath sourceValues = createString("sourceValues");

    public final StringPath targetQuestionIdentifier = createString("targetQuestionIdentifier");

    public final StringPath targetType = createString("targetType");

    public final StringPath userRuleId = createString("userRuleId");

    public final QWaTemplate waTemplateUid;

    public QWaRuleMetadatum(String variable) {
        this(WaRuleMetadatum.class, forVariable(variable), INITS);
    }

    public QWaRuleMetadatum(Path<? extends WaRuleMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaRuleMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaRuleMetadatum(PathMetadata metadata, PathInits inits) {
        this(WaRuleMetadatum.class, metadata, inits);
    }

    public QWaRuleMetadatum(Class<? extends WaRuleMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateUid = inits.isInitialized("waTemplateUid") ? new QWaTemplate(forProperty("waTemplateUid")) : null;
    }

}

