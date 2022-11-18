package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QManufacturedMaterialHist is a Querydsl query type for ManufacturedMaterialHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManufacturedMaterialHist extends EntityPathBase<ManufacturedMaterialHist> {

    private static final long serialVersionUID = -1548893757L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManufacturedMaterialHist manufacturedMaterialHist = new QManufacturedMaterialHist("manufacturedMaterialHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final DateTimePath<java.time.Instant> expirationTime = createDateTime("expirationTime", java.time.Instant.class);

    public final QManufacturedMaterialHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath lotNm = createString("lotNm");

    public final QManufacturedMaterial manufacturedMaterial;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath stabilityDurationCd = createString("stabilityDurationCd");

    public final StringPath stabilityDurationUnitCd = createString("stabilityDurationUnitCd");

    public final DateTimePath<java.time.Instant> stabilityFromTime = createDateTime("stabilityFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> stabilityToTime = createDateTime("stabilityToTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QManufacturedMaterialHist(String variable) {
        this(ManufacturedMaterialHist.class, forVariable(variable), INITS);
    }

    public QManufacturedMaterialHist(Path<? extends ManufacturedMaterialHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QManufacturedMaterialHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QManufacturedMaterialHist(PathMetadata metadata, PathInits inits) {
        this(ManufacturedMaterialHist.class, metadata, inits);
    }

    public QManufacturedMaterialHist(Class<? extends ManufacturedMaterialHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QManufacturedMaterialHistId(forProperty("id")) : null;
        this.manufacturedMaterial = inits.isInitialized("manufacturedMaterial") ? new QManufacturedMaterial(forProperty("manufacturedMaterial"), inits.get("manufacturedMaterial")) : null;
    }

}

