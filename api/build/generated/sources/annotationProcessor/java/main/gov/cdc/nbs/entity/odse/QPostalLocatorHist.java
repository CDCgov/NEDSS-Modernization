package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostalLocatorHist is a Querydsl query type for PostalLocatorHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostalLocatorHist extends EntityPathBase<PostalLocatorHist> {

    private static final long serialVersionUID = 2111758710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostalLocatorHist postalLocatorHist = new QPostalLocatorHist("postalLocatorHist");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath censusBlockCd = createString("censusBlockCd");

    public final StringPath censusMinorCivilDivisionCd = createString("censusMinorCivilDivisionCd");

    public final StringPath censusTrackCd = createString("censusTrackCd");

    public final StringPath censusTract = createString("censusTract");

    public final StringPath cityCd = createString("cityCd");

    public final StringPath cityDescTxt = createString("cityDescTxt");

    public final StringPath cntryCd = createString("cntryCd");

    public final StringPath cntryDescTxt = createString("cntryDescTxt");

    public final StringPath cntyCd = createString("cntyCd");

    public final StringPath cntyDescTxt = createString("cntyDescTxt");

    public final StringPath geocodeMatchInd = createString("geocodeMatchInd");

    public final QPostalLocatorHistId id;

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath msaCongressDistrictCd = createString("msaCongressDistrictCd");

    public final QPostalLocator postalLocatorUid;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath regionDistrictCd = createString("regionDistrictCd");

    public final StringPath stateCd = createString("stateCd");

    public final StringPath streetAddr1 = createString("streetAddr1");

    public final StringPath streetAddr2 = createString("streetAddr2");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public final StringPath withinCityLimitsInd = createString("withinCityLimitsInd");

    public final StringPath zipCd = createString("zipCd");

    public QPostalLocatorHist(String variable) {
        this(PostalLocatorHist.class, forVariable(variable), INITS);
    }

    public QPostalLocatorHist(Path<? extends PostalLocatorHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostalLocatorHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostalLocatorHist(PathMetadata metadata, PathInits inits) {
        this(PostalLocatorHist.class, metadata, inits);
    }

    public QPostalLocatorHist(Class<? extends PostalLocatorHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QPostalLocatorHistId(forProperty("id")) : null;
        this.postalLocatorUid = inits.isInitialized("postalLocatorUid") ? new QPostalLocator(forProperty("postalLocatorUid")) : null;
    }

}

