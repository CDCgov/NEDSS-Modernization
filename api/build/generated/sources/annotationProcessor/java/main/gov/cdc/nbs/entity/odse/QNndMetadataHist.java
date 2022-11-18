package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNndMetadataHist is a Querydsl query type for NndMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNndMetadataHist extends EntityPathBase<NndMetadataHist> {

    private static final long serialVersionUID = 1664150622L;

    public static final QNndMetadataHist nndMetadataHist = new QNndMetadataHist("nndMetadataHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath hl7SegmentField = createString("hl7SegmentField");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath indicatorCd = createString("indicatorCd");

    public final StringPath investigationFormCd = createString("investigationFormCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final ComparablePath<Character> msgTriggerIndCd = createComparable("msgTriggerIndCd", Character.class);

    public final NumberPath<Long> nbsPageUid = createNumber("nbsPageUid", Long.class);

    public final NumberPath<Long> nbsUiMetadataUid = createNumber("nbsUiMetadataUid", Long.class);

    public final NumberPath<Long> nndMetadataUid = createNumber("nndMetadataUid", Long.class);

    public final StringPath orderGroupId = createString("orderGroupId");

    public final StringPath partTypeCd = createString("partTypeCd");

    public final StringPath questionDataTypeNnd = createString("questionDataTypeNnd");

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath questionIdentifierNnd = createString("questionIdentifierNnd");

    public final StringPath questionLabelNnd = createString("questionLabelNnd");

    public final StringPath questionMap = createString("questionMap");

    public final NumberPath<Integer> questionOrderNnd = createNumber("questionOrderNnd", Integer.class);

    public final ComparablePath<Character> questionRequiredNnd = createComparable("questionRequiredNnd", Character.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Integer> repeatGroupSeqNbr = createNumber("repeatGroupSeqNbr", Integer.class);

    public final StringPath translationTableNm = createString("translationTableNm");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public final StringPath xmlDataType = createString("xmlDataType");

    public final StringPath xmlPath = createString("xmlPath");

    public final StringPath xmlTag = createString("xmlTag");

    public QNndMetadataHist(String variable) {
        super(NndMetadataHist.class, forVariable(variable));
    }

    public QNndMetadataHist(Path<? extends NndMetadataHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNndMetadataHist(PathMetadata metadata) {
        super(NndMetadataHist.class, metadata);
    }

}

