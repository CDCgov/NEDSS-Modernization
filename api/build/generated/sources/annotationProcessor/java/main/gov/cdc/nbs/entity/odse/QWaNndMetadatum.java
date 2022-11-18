package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaNndMetadatum is a Querydsl query type for WaNndMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaNndMetadatum extends EntityPathBase<WaNndMetadatum> {

    private static final long serialVersionUID = -1457929881L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaNndMetadatum waNndMetadatum = new QWaNndMetadatum("waNndMetadatum");

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

    public final QWaTemplate waTemplateUid;

    public final QWaUiMetadatum waUiMetadataUid;

    public final StringPath xmlDataType = createString("xmlDataType");

    public final StringPath xmlPath = createString("xmlPath");

    public final StringPath xmlTag = createString("xmlTag");

    public QWaNndMetadatum(String variable) {
        this(WaNndMetadatum.class, forVariable(variable), INITS);
    }

    public QWaNndMetadatum(Path<? extends WaNndMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaNndMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaNndMetadatum(PathMetadata metadata, PathInits inits) {
        this(WaNndMetadatum.class, metadata, inits);
    }

    public QWaNndMetadatum(Class<? extends WaNndMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateUid = inits.isInitialized("waTemplateUid") ? new QWaTemplate(forProperty("waTemplateUid")) : null;
        this.waUiMetadataUid = inits.isInitialized("waUiMetadataUid") ? new QWaUiMetadatum(forProperty("waUiMetadataUid"), inits.get("waUiMetadataUid")) : null;
    }

}

