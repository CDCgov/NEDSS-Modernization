package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManufacturedMaterial is a Querydsl query type for ManufacturedMaterial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacturedMaterial extends EntityPathBase<ManufacturedMaterial> {

    private static final long serialVersionUID = -1379870975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManufacturedMaterial manufacturedMaterial = new QManufacturedMaterial("manufacturedMaterial");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> expirationTime = createDateTime("expirationTime", java.time.Instant.class);

    public final QManufacturedMaterialId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath lotNm = createString("lotNm");

    public final QMaterial materialUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath stabilityDurationCd = createString("stabilityDurationCd");

    public final StringPath stabilityDurationUnitCd = createString("stabilityDurationUnitCd");

    public final DateTimePath<java.time.Instant> stabilityFromTime = createDateTime("stabilityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> stabilityToTime = createDateTime("stabilityToTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QManufacturedMaterial(String variable) {
        this(ManufacturedMaterial.class, forVariable(variable), INITS);
    }

    public QManufacturedMaterial(Path<? extends ManufacturedMaterial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManufacturedMaterial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManufacturedMaterial(PathMetadata metadata, PathInits inits) {
        this(ManufacturedMaterial.class, metadata, inits);
    }

    public QManufacturedMaterial(Class<? extends ManufacturedMaterial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QManufacturedMaterialId(forProperty("id")) : null;
        this.materialUid = inits.isInitialized("materialUid") ? new QMaterial(forProperty("materialUid"), inits.get("materialUid")) : null;
    }

}

