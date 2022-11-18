package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStateDefinedFieldMetadatum is a Querydsl query type for StateDefinedFieldMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStateDefinedFieldMetadatum extends EntityPathBase<StateDefinedFieldMetadatum> {

    private static final long serialVersionUID = 431461311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStateDefinedFieldMetadatum stateDefinedFieldMetadatum = new QStateDefinedFieldMetadatum("stateDefinedFieldMetadatum");

    public final ComparablePath<Character> activeInd = createComparable("activeInd", Character.class);

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath adminComment = createString("adminComment");

    public final StringPath businessObjectNm = createString("businessObjectNm");

    public final StringPath categoryType = createString("categoryType");

    public final StringPath cdcNationalId = createString("cdcNationalId");

    public final StringPath classCd = createString("classCd");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath conditionDescTxt = createString("conditionDescTxt");

    public final QCustomSubformMetadatum customSubformMetadataUid;

    public final StringPath dataType = createString("dataType");

    public final StringPath deploymentCd = createString("deploymentCd");

    public final NumberPath<Integer> displayOrderNbr = createNumber("displayOrderNbr", Integer.class);

    public final StringPath fieldSize = createString("fieldSize");

    public final StringPath htmlTag = createString("htmlTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> importVersionNbr = createNumber("importVersionNbr", Long.class);

    public final StringPath labelTxt = createString("labelTxt");

    public final StringPath ldfOid = createString("ldfOid");

    public final StringPath ldfPageId = createString("ldfPageId");

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final ComparablePath<Character> nndInd = createComparable("nndInd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> requiredInd = createComparable("requiredInd", Character.class);

    public final StringPath stateCd = createString("stateCd");

    public final StringPath validationJscriptTxt = createString("validationJscriptTxt");

    public final StringPath validationTxt = createString("validationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QStateDefinedFieldMetadatum(String variable) {
        this(StateDefinedFieldMetadatum.class, forVariable(variable), INITS);
    }

    public QStateDefinedFieldMetadatum(Path<? extends StateDefinedFieldMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStateDefinedFieldMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStateDefinedFieldMetadatum(PathMetadata metadata, PathInits inits) {
        this(StateDefinedFieldMetadatum.class, metadata, inits);
    }

    public QStateDefinedFieldMetadatum(Class<? extends StateDefinedFieldMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customSubformMetadataUid = inits.isInitialized("customSubformMetadataUid") ? new QCustomSubformMetadatum(forProperty("customSubformMetadataUid")) : null;
    }

}

