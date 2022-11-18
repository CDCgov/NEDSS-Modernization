package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrganization is a Querydsl query type for Organization
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrganization extends EntityPathBase<Organization> {

    private static final long serialVersionUID = 1381511466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrganization organization = new QOrganization("organization");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath cityCd = createString("cityCd");

    public final StringPath cityDescTxt = createString("cityDescTxt");

    public final StringPath cntryCd = createString("cntryCd");

    public final StringPath cntyCd = createString("cntyCd");

    public final StringPath description = createString("description");

    public final StringPath displayNm = createString("displayNm");

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final StringPath edxInd = createString("edxInd");

    public final ComparablePath<Character> electronicInd = createComparable("electronicInd", Character.class);

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNBSEntity NBSEntity;

    public final StringPath phoneCntryCd = createString("phoneCntryCd");

    public final StringPath phoneNbr = createString("phoneNbr");

    public final EnumPath<gov.cdc.nbs.entity.enums.RecordStatus> recordStatusCd = createEnum("recordStatusCd", gov.cdc.nbs.entity.enums.RecordStatus.class);

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath standardIndustryClassCd = createString("standardIndustryClassCd");

    public final StringPath standardIndustryDescTxt = createString("standardIndustryDescTxt");

    public final StringPath stateCd = createString("stateCd");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath streetAddr1 = createString("streetAddr1");

    public final StringPath streetAddr2 = createString("streetAddr2");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public final StringPath zipCd = createString("zipCd");

    public QOrganization(String variable) {
        this(Organization.class, forVariable(variable), INITS);
    }

    public QOrganization(Path<? extends Organization> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrganization(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrganization(PathMetadata metadata, PathInits inits) {
        this(Organization.class, metadata, inits);
    }

    public QOrganization(Class<? extends Organization> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.NBSEntity = inits.isInitialized("NBSEntity") ? new QNBSEntity(forProperty("NBSEntity")) : null;
    }

}

