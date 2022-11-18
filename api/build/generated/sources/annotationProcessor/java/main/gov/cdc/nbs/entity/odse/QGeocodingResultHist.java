package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeocodingResultHist is a Querydsl query type for GeocodingResultHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeocodingResultHist extends EntityPathBase<GeocodingResultHist> {

    private static final long serialVersionUID = 55336291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGeocodingResultHist geocodingResultHist = new QGeocodingResultHist("geocodingResultHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath censusBlockId = createString("censusBlockId");

    public final StringPath city = createString("city");

    public final StringPath cntyCd = createString("cntyCd");

    public final StringPath country = createString("country");

    public final StringPath dataType = createString("dataType");

    public final StringPath entityClassCd = createString("entityClassCd");

    public final NumberPath<Long> entityUid = createNumber("entityUid", Long.class);

    public final StringPath firmName = createString("firmName");

    public final QGeocodingResult geocodingResultUid;

    public final StringPath houseNumber = createString("houseNumber");

    public final QGeocodingResultHistId id;

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Integer> latitude = createNumber("latitude", Integer.class);

    public final StringPath locationQualityCd = createString("locationQualityCd");

    public final NumberPath<Integer> longitude = createNumber("longitude", Integer.class);

    public final StringPath matchCd = createString("matchCd");

    public final NumberPath<Short> nextScoreCount = createNumber("nextScoreCount", Short.class);

    public final NumberPath<java.math.BigDecimal> nextScoreDiff = createNumber("nextScoreDiff", java.math.BigDecimal.class);

    public final NumberPath<Short> numMatches = createNumber("numMatches", Short.class);

    public final NumberPath<Long> postalLocatorUid = createNumber("postalLocatorUid", Long.class);

    public final StringPath postfixDirection = createString("postfixDirection");

    public final StringPath prefixDirection = createString("prefixDirection");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath resultType = createString("resultType");

    public final NumberPath<java.math.BigDecimal> score = createNumber("score", java.math.BigDecimal.class);

    public final StringPath segmentId = createString("segmentId");

    public final StringPath state = createString("state");

    public final StringPath streetAddr1 = createString("streetAddr1");

    public final StringPath streetAddr2 = createString("streetAddr2");

    public final StringPath streetName = createString("streetName");

    public final StringPath streetType = createString("streetType");

    public final StringPath unitNumber = createString("unitNumber");

    public final StringPath unitType = createString("unitType");

    public final StringPath zip4Cd = createString("zip4Cd");

    public final StringPath zip5Cd = createString("zip5Cd");

    public final StringPath zipCd = createString("zipCd");

    public QGeocodingResultHist(String variable) {
        this(GeocodingResultHist.class, forVariable(variable), INITS);
    }

    public QGeocodingResultHist(Path<? extends GeocodingResultHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGeocodingResultHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGeocodingResultHist(PathMetadata metadata, PathInits inits) {
        this(GeocodingResultHist.class, metadata, inits);
    }

    public QGeocodingResultHist(Class<? extends GeocodingResultHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.geocodingResultUid = inits.isInitialized("geocodingResultUid") ? new QGeocodingResult(forProperty("geocodingResultUid"), inits.get("geocodingResultUid")) : null;
        this.id = inits.isInitialized("id") ? new QGeocodingResultHistId(forProperty("id")) : null;
    }

}

