package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaceHist is a Querydsl query type for PlaceHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaceHist extends EntityPathBase<PlaceHist> {

    private static final long serialVersionUID = 2008967218L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaceHist placeHist = new QPlaceHist("placeHist");

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

    public final StringPath durationAmt = createString("durationAmt");

    public final StringPath durationUnitCd = createString("durationUnitCd");

    public final DateTimePath<java.time.Instant> fromTime = createDateTime("fromTime", java.time.Instant.class);

    public final QPlaceHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath nm = createString("nm");

    public final StringPath phoneCntryCd = createString("phoneCntryCd");

    public final StringPath phoneNbr = createString("phoneNbr");

    public final QPlace placeUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath stateCd = createString("stateCd");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath streetAddr1 = createString("streetAddr1");

    public final StringPath streetAddr2 = createString("streetAddr2");

    public final DateTimePath<java.time.Instant> toTime = createDateTime("toTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath zipCd = createString("zipCd");

    public QPlaceHist(String variable) {
        this(PlaceHist.class, forVariable(variable), INITS);
    }

    public QPlaceHist(Path<? extends PlaceHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaceHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaceHist(PathMetadata metadata, PathInits inits) {
        this(PlaceHist.class, metadata, inits);
    }

    public QPlaceHist(Class<? extends PlaceHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPlaceHistId(forProperty("id")) : null;
        this.placeUid = inits.isInitialized("placeUid") ? new QPlace(forProperty("placeUid"), inits.get("placeUid")) : null;
    }

}

