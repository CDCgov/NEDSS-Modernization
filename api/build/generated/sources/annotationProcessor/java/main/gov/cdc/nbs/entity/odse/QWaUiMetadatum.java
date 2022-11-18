package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaUiMetadatum is a Querydsl query type for WaUiMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaUiMetadatum extends EntityPathBase<WaUiMetadatum> {

    private static final long serialVersionUID = 428848917L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaUiMetadatum waUiMetadatum = new QWaUiMetadatum("waUiMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComment = createString("adminComment");

    public final ComparablePath<Character> batchTableAppearIndCd = createComparable("batchTableAppearIndCd", Character.class);

    public final NumberPath<Integer> batchTableColumnWidth = createNumber("batchTableColumnWidth", Integer.class);

    public final StringPath batchTableHeader = createString("batchTableHeader");

    public final StringPath blockNm = createString("blockNm");

    public final NumberPath<Long> codeSetGroupId = createNumber("codeSetGroupId", Long.class);

    public final ComparablePath<Character> coinfectionIndCd = createComparable("coinfectionIndCd", Character.class);

    public final StringPath dataCd = createString("dataCd");

    public final StringPath dataLocation = createString("dataLocation");

    public final StringPath dataType = createString("dataType");

    public final StringPath dataUseCd = createString("dataUseCd");

    public final StringPath defaultValue = createString("defaultValue");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath displayInd = createString("displayInd");

    public final StringPath enableInd = createString("enableInd");

    public final StringPath entryMethod = createString("entryMethod");

    public final StringPath fieldSize = createString("fieldSize");

    public final ComparablePath<Character> futureDateIndCd = createComparable("futureDateIndCd", Character.class);

    public final StringPath groupNm = createString("groupNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath legacyDataLocation = createString("legacyDataLocation");

    public final StringPath localId = createString("localId");

    public final StringPath mask = createString("mask");

    public final NumberPath<Long> maxLength = createNumber("maxLength", Long.class);

    public final NumberPath<Long> maxValue = createNumber("maxValue", Long.class);

    public final NumberPath<Long> minValue = createNumber("minValue", Long.class);

    public final NumberPath<Long> nbsUiComponentUid = createNumber("nbsUiComponentUid", Long.class);

    public final NumberPath<Integer> orderNbr = createNumber("orderNbr", Integer.class);

    public final ComparablePath<Character> otherValueIndCd = createComparable("otherValueIndCd", Character.class);

    public final NumberPath<Long> parentUid = createNumber("parentUid", Long.class);

    public final StringPath partTypeCd = createString("partTypeCd");

    public final ComparablePath<Character> publishIndCd = createComparable("publishIndCd", Character.class);

    public final NumberPath<Integer> questionGroupSeqNbr = createNumber("questionGroupSeqNbr", Integer.class);

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath questionLabel = createString("questionLabel");

    public final StringPath questionNm = createString("questionNm");

    public final StringPath questionOid = createString("questionOid");

    public final StringPath questionOidSystemTxt = createString("questionOidSystemTxt");

    public final StringPath questionToolTip = createString("questionToolTip");

    public final StringPath questionType = createString("questionType");

    public final StringPath questionUnitIdentifier = createString("questionUnitIdentifier");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> repeatsIndCd = createComparable("repeatsIndCd", Character.class);

    public final StringPath requiredInd = createString("requiredInd");

    public final ComparablePath<Character> standardNndIndCd = createComparable("standardNndIndCd", Character.class);

    public final ComparablePath<Character> standardQuestionIndCd = createComparable("standardQuestionIndCd", Character.class);

    public final StringPath subGroupNm = createString("subGroupNm");

    public final StringPath unitParentIdentifier = createString("unitParentIdentifier");

    public final StringPath unitTypeCd = createString("unitTypeCd");

    public final StringPath unitValue = createString("unitValue");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public final QWaTemplate waTemplateUid;

    public QWaUiMetadatum(String variable) {
        this(WaUiMetadatum.class, forVariable(variable), INITS);
    }

    public QWaUiMetadatum(Path<? extends WaUiMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaUiMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaUiMetadatum(PathMetadata metadata, PathInits inits) {
        this(WaUiMetadatum.class, metadata, inits);
    }

    public QWaUiMetadatum(Class<? extends WaUiMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateUid = inits.isInitialized("waTemplateUid") ? new QWaTemplate(forProperty("waTemplateUid")) : null;
    }

}

