package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGeocodingResult is a Querydsl query type for GeocodingResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGeocodingResult extends EntityPathBase<GeocodingResult> {

    private static final long serialVersionUID = 658391713L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGeocodingResult geocodingResult = new QGeocodingResult("geocodingResult");

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

    public final StringPath houseNumber = createString("houseNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final NumberPath<Integer> latitude = createNumber("latitude", Integer.class);

    public final StringPath locationQualityCd = createString("locationQualityCd");

    public final NumberPath<Integer> longitude = createNumber("longitude", Integer.class);

    public final StringPath matchCd = createString("matchCd");

    public final NumberPath<Short> nextScoreCount = createNumber("nextScoreCount", Short.class);

    public final NumberPath<java.math.BigDecimal> nextScoreDiff = createNumber("nextScoreDiff", java.math.BigDecimal.class);

    public final NumberPath<Short> numMatches = createNumber("numMatches", Short.class);

    public final QPostalLocator postalLocatorUid;

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

    public QGeocodingResult(String variable) {
        this(GeocodingResult.class, forVariable(variable), INITS);
    }

    public QGeocodingResult(Path<? extends GeocodingResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGeocodingResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGeocodingResult(PathMetadata metadata, PathInits inits) {
        this(GeocodingResult.class, metadata, inits);
    }

    public QGeocodingResult(Class<? extends GeocodingResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postalLocatorUid = inits.isInitialized("postalLocatorUid") ? new QPostalLocator(forProperty("postalLocatorUid")) : null;
    }

}

