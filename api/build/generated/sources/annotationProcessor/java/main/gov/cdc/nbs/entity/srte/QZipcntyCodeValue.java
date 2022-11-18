package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QZipcntyCodeValue is a Querydsl query type for ZipcntyCodeValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QZipcntyCodeValue extends EntityPathBase<ZipcntyCodeValue> {

    private static final long serialVersionUID = 198881569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QZipcntyCodeValue zipcntyCodeValue = new QZipcntyCodeValue("zipcntyCodeValue");

    public final QStateCountyCodeValue cntyCode;

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QZipcntyCodeValueId id;

    public final QZipCodeValue zipCode;

    public QZipcntyCodeValue(String variable) {
        this(ZipcntyCodeValue.class, forVariable(variable), INITS);
    }

    public QZipcntyCodeValue(Path<? extends ZipcntyCodeValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QZipcntyCodeValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QZipcntyCodeValue(PathMetadata metadata, PathInits inits) {
        this(ZipcntyCodeValue.class, metadata, inits);
    }

    public QZipcntyCodeValue(Class<? extends ZipcntyCodeValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cntyCode = inits.isInitialized("cntyCode") ? new QStateCountyCodeValue(forProperty("cntyCode")) : null;
        this.id = inits.isInitialized("id") ? new QZipcntyCodeValueId(forProperty("id")) : null;
        this.zipCode = inits.isInitialized("zipCode") ? new QZipCodeValue(forProperty("zipCode")) : null;
    }

}

