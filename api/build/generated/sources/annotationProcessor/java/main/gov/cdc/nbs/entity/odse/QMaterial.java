package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMaterial is a Querydsl query type for Material
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMaterial extends EntityPathBase<Material> {

    private static final long serialVersionUID = -1910686562L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMaterial material = new QMaterial("material");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath description = createString("description");

    public final StringPath effectiveDurationAmt = createString("effectiveDurationAmt");

    public final StringPath effectiveDurationUnitCd = createString("effectiveDurationUnitCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath formCd = createString("formCd");

    public final StringPath formDescTxt = createString("formDescTxt");

    public final StringPath handlingCd = createString("handlingCd");

    public final StringPath handlingDescTxt = createString("handlingDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNBSEntity NBSEntity;

    public final StringPath nm = createString("nm");

    public final StringPath qty = createString("qty");

    public final StringPath qtyUnitCd = createString("qtyUnitCd");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath riskCd = createString("riskCd");

    public final StringPath riskDescTxt = createString("riskDescTxt");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QMaterial(String variable) {
        this(Material.class, forVariable(variable), INITS);
    }

    public QMaterial(Path<? extends Material> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMaterial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMaterial(PathMetadata metadata, PathInits inits) {
        this(Material.class, metadata, inits);
    }

    public QMaterial(Class<? extends Material> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.NBSEntity = inits.isInitialized("NBSEntity") ? new QNBSEntity(forProperty("NBSEntity")) : null;
    }

}

