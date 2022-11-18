package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWaQuestion is a Querydsl query type for WaQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaQuestion extends EntityPathBase<WaQuestion> {

    private static final long serialVersionUID = 195041927L;

    public static final QWaQuestion waQuestion = new QWaQuestion("waQuestion");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComment = createString("adminComment");

    public final NumberPath<Long> codeSetGroupId = createNumber("codeSetGroupId", Long.class);

    public final ComparablePath<Character> coinfectionIndCd = createComparable("coinfectionIndCd", Character.class);

    public final StringPath dataCd = createString("dataCd");

    public final StringPath dataLocation = createString("dataLocation");

    public final StringPath dataType = createString("dataType");

    public final StringPath dataUseCd = createString("dataUseCd");

    public final StringPath defaultValue = createString("defaultValue");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath entryMethod = createString("entryMethod");

    public final StringPath fieldSize = createString("fieldSize");

    public final ComparablePath<Character> futureDateIndCd = createComparable("futureDateIndCd", Character.class);

    public final StringPath groupNm = createString("groupNm");

    public final StringPath hl7SegmentField = createString("hl7SegmentField");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath legacyDataLocation = createString("legacyDataLocation");

    public final StringPath legacyQuestionIdentifier = createString("legacyQuestionIdentifier");

    public final StringPath localId = createString("localId");

    public final StringPath mask = createString("mask");

    public final NumberPath<Long> maxValue = createNumber("maxValue", Long.class);

    public final NumberPath<Long> minValue = createNumber("minValue", Long.class);

    public final NumberPath<Long> nbsUiComponentUid = createNumber("nbsUiComponentUid", Long.class);

    public final ComparablePath<Character> nndMsgInd = createComparable("nndMsgInd", Character.class);

    public final StringPath orderGroupId = createString("orderGroupId");

    public final ComparablePath<Character> otherValueIndCd = createComparable("otherValueIndCd", Character.class);

    public final StringPath partTypeCd = createString("partTypeCd");

    public final StringPath questionDataTypeNnd = createString("questionDataTypeNnd");

    public final NumberPath<Integer> questionGroupSeqNbr = createNumber("questionGroupSeqNbr", Integer.class);

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath questionIdentifierNnd = createString("questionIdentifierNnd");

    public final StringPath questionLabel = createString("questionLabel");

    public final StringPath questionLabelNnd = createString("questionLabelNnd");

    public final StringPath questionNm = createString("questionNm");

    public final StringPath questionOid = createString("questionOid");

    public final StringPath questionOidSystemTxt = createString("questionOidSystemTxt");

    public final ComparablePath<Character> questionRequiredNnd = createComparable("questionRequiredNnd", Character.class);

    public final StringPath questionToolTip = createString("questionToolTip");

    public final StringPath questionType = createString("questionType");

    public final StringPath questionUnitIdentifier = createString("questionUnitIdentifier");

    public final StringPath rdbColumnNm = createString("rdbColumnNm");

    public final StringPath rdbTableNm = createString("rdbTableNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final ComparablePath<Character> repeatsIndCd = createComparable("repeatsIndCd", Character.class);

    public final StringPath rptAdminColumnNm = createString("rptAdminColumnNm");

    public final StringPath sourceNm = createString("sourceNm");

    public final ComparablePath<Character> standardNndIndCd = createComparable("standardNndIndCd", Character.class);

    public final ComparablePath<Character> standardQuestionIndCd = createComparable("standardQuestionIndCd", Character.class);

    public final StringPath subGroupNm = createString("subGroupNm");

    public final StringPath unitParentIdentifier = createString("unitParentIdentifier");

    public final StringPath unitTypeCd = createString("unitTypeCd");

    public final StringPath unitValue = createString("unitValue");

    public final StringPath userDefinedColumnNm = createString("userDefinedColumnNm");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public QWaQuestion(String variable) {
        super(WaQuestion.class, forVariable(variable));
    }

    public QWaQuestion(Path<? extends WaQuestion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWaQuestion(PathMetadata metadata) {
        super(WaQuestion.class, metadata);
    }

}

