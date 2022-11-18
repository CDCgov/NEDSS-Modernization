package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWaTemplate is a Querydsl query type for WaTemplate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaTemplate extends EntityPathBase<WaTemplate> {

    private static final long serialVersionUID = 39365403L;

    public static final QWaTemplate waTemplate = new QWaTemplate("waTemplate");

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

    public final StringPath xmlPayload = createString("xmlPayload");

    public QWaTemplate(String variable) {
        super(WaTemplate.class, forVariable(variable));
    }

    public QWaTemplate(Path<? extends WaTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWaTemplate(PathMetadata metadata) {
        super(WaTemplate.class, metadata);
    }

}

