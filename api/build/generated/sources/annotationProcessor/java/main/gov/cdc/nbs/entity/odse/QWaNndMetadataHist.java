package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaNndMetadataHist is a Querydsl query type for WaNndMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaNndMetadataHist extends EntityPathBase<WaNndMetadataHist> {

    private static final long serialVersionUID = 1795711476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaNndMetadataHist waNndMetadataHist = new QWaNndMetadataHist("waNndMetadataHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath hl7SegmentField = createString("hl7SegmentField");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath indicatorCd = createString("indicatorCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

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

    public final NumberPath<Long> waNndMetadataUid = createNumber("waNndMetadataUid", Long.class);

    public final QWaTemplateHist waTemplateHistUid;

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public final NumberPath<Long> waUiMetadataUid = createNumber("waUiMetadataUid", Long.class);

    public final StringPath xmlDataType = createString("xmlDataType");

    public final StringPath xmlPath = createString("xmlPath");

    public final StringPath xmlTag = createString("xmlTag");

    public QWaNndMetadataHist(String variable) {
        this(WaNndMetadataHist.class, forVariable(variable), INITS);
    }

    public QWaNndMetadataHist(Path<? extends WaNndMetadataHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaNndMetadataHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaNndMetadataHist(PathMetadata metadata, PathInits inits) {
        this(WaNndMetadataHist.class, metadata, inits);
    }

    public QWaNndMetadataHist(Class<? extends WaNndMetadataHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateHistUid = inits.isInitialized("waTemplateHistUid") ? new QWaTemplateHist(forProperty("waTemplateHistUid")) : null;
    }

}

