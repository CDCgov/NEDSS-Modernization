package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWaTemplateHist is a Querydsl query type for WaTemplateHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaTemplateHist extends EntityPathBase<WaTemplateHist> {

    private static final long serialVersionUID = -2119567139L;

    public static final QWaTemplateHist waTemplateHist = new QWaTemplateHist("waTemplateHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath busObjType = createString("busObjType");

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath datamartNm = createString("datamartNm");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath formCd = createString("formCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath nndEntityIdentifier = createString("nndEntityIdentifier");

    public final NumberPath<Long> parentTemplateUid = createNumber("parentTemplateUid", Long.class);

    public final ComparablePath<Character> publishIndCd = createComparable("publishIndCd", Character.class);

    public final NumberPath<Integer> publishVersionNbr = createNumber("publishVersionNbr", Integer.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath sourceNm = createString("sourceNm");

    public final StringPath templateNm = createString("templateNm");

    public final StringPath templateType = createString("templateType");

    public final NumberPath<Integer> templateVersionNbr = createNumber("templateVersionNbr", Integer.class);

    public final StringPath versionNote = createString("versionNote");

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public final StringPath xmlPayload = createString("xmlPayload");

    public QWaTemplateHist(String variable) {
        super(WaTemplateHist.class, forVariable(variable));
    }

    public QWaTemplateHist(Path<? extends WaTemplateHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWaTemplateHist(PathMetadata metadata) {
        super(WaTemplateHist.class, metadata);
    }

}

