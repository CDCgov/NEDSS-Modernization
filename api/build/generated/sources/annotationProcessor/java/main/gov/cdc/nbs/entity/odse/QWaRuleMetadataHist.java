package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWaRuleMetadataHist is a Querydsl query type for WaRuleMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaRuleMetadataHist extends EntityPathBase<WaRuleMetadataHist> {

    private static final long serialVersionUID = -1151033938L;

    public static final QWaRuleMetadataHist waRuleMetadataHist = new QWaRuleMetadataHist("waRuleMetadataHist");

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

    public final NumberPath<Long> waRuleMetadataUid = createNumber("waRuleMetadataUid", Long.class);

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public QWaRuleMetadataHist(String variable) {
        super(WaRuleMetadataHist.class, forVariable(variable));
    }

    public QWaRuleMetadataHist(Path<? extends WaRuleMetadataHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWaRuleMetadataHist(PathMetadata metadata) {
        super(WaRuleMetadataHist.class, metadata);
    }

}

