package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsUiMetadataHist is a Querydsl query type for NbsUiMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsUiMetadataHist extends EntityPathBase<NbsUiMetadataHist> {

    private static final long serialVersionUID = 222182701L;

    public static final QNbsUiMetadataHist nbsUiMetadataHist = new QNbsUiMetadataHist("nbsUiMetadataHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComment = createString("adminComment");

    public final ComparablePath<Character> batchTableAppearIndCd = createComparable("batchTableAppearIndCd", Character.class);

    public final NumberPath<Integer> batchTableColumnWidth = createNumber("batchTableColumnWidth", Integer.class);

    public final StringPath batchTableHeader = createString("batchTableHeader");

    public final StringPath blockNm = createString("blockNm");

    public final NumberPath<Long> codeSetGroupId = createNumber("codeSetGroupId", Long.class);

    public final ComparablePath<Character> coinfectionIndCd = createComparable("coinfectionIndCd", Character.class);

    public final StringPath cssStyle = createString("cssStyle");

    public final StringPath dataCd = createString("dataCd");

    public final StringPath dataLocation = createString("dataLocation");

    public final StringPath dataType = createString("dataType");

    public final StringPath dataUseCd = createString("dataUseCd");

    public final StringPath defaultValue = createString("defaultValue");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath displayInd = createString("displayInd");

    public final StringPath enableInd = createString("enableInd");

    public final StringPath fieldSize = createString("fieldSize");

    public final ComparablePath<Character> futureDateIndCd = createComparable("futureDateIndCd", Character.class);

    public final StringPath groupNm = createString("groupNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath investigationFormCd = createString("investigationFormCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath ldfPageId = createString("ldfPageId");

    public final StringPath ldfPosition = createString("ldfPosition");

    public final StringPath ldfStatusCd = createString("ldfStatusCd");

    public final DateTimePath<java.time.Instant> ldfStatusTime = createDateTime("ldfStatusTime", java.time.Instant.class);

    public final StringPath legacyDataLocation = createString("legacyDataLocation");

    public final StringPath localId = createString("localId");

    public final StringPath mask = createString("mask");

    public final NumberPath<Long> maxLength = createNumber("maxLength", Long.class);

    public final NumberPath<Long> maxValue = createNumber("maxValue", Long.class);

    public final NumberPath<Long> minValue = createNumber("minValue", Long.class);

    public final NumberPath<Long> nbsPageUid = createNumber("nbsPageUid", Long.class);

    public final NumberPath<Long> nbsQuestionUid = createNumber("nbsQuestionUid", Long.class);

    public final NumberPath<Long> nbsTableUid = createNumber("nbsTableUid", Long.class);

    public final NumberPath<Long> nbsUiComponentUid = createNumber("nbsUiComponentUid", Long.class);

    public final NumberPath<Long> nbsUiMetadataUid = createNumber("nbsUiMetadataUid", Long.class);

    public final NumberPath<Integer> orderNbr = createNumber("orderNbr", Integer.class);

    public final ComparablePath<Character> otherValueIndCd = createComparable("otherValueIndCd", Character.class);

    public final NumberPath<Long> parentUid = createNumber("parentUid", Long.class);

    public final StringPath partTypeCd = createString("partTypeCd");

    public final NumberPath<Integer> questionGroupSeqNbr = createNumber("questionGroupSeqNbr", Integer.class);

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath questionLabel = createString("questionLabel");

    public final StringPath questionOid = createString("questionOid");

    public final StringPath questionOidSystemTxt = createString("questionOidSystemTxt");

    public final StringPath questionToolTip = createString("questionToolTip");

    public final StringPath questionUnitIdentifier = createString("questionUnitIdentifier");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> repeatsIndCd = createComparable("repeatsIndCd", Character.class);

    public final StringPath requiredInd = createString("requiredInd");

    public final ComparablePath<Character> standardNndIndCd = createComparable("standardNndIndCd", Character.class);

    public final StringPath subGroupNm = createString("subGroupNm");

    public final StringPath tabName = createString("tabName");

    public final NumberPath<Integer> tabOrderId = createNumber("tabOrderId", Integer.class);

    public final StringPath unitParentIdentifier = createString("unitParentIdentifier");

    public final StringPath unitTypeCd = createString("unitTypeCd");

    public final StringPath unitValue = createString("unitValue");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public QNbsUiMetadataHist(String variable) {
        super(NbsUiMetadataHist.class, forVariable(variable));
    }

    public QNbsUiMetadataHist(Path<? extends NbsUiMetadataHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsUiMetadataHist(PathMetadata metadata) {
        super(NbsUiMetadataHist.class, metadata);
    }

}

