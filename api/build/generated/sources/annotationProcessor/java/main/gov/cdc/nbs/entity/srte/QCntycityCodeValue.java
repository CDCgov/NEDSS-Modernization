package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCntycityCodeValue is a Querydsl query type for CntycityCodeValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCntycityCodeValue extends EntityPathBase<CntycityCodeValue> {

    private static final long serialVersionUID = 1380842619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCntycityCodeValue cntycityCodeValue = new QCntycityCodeValue("cntycityCodeValue");

    public final QCityCodeValue cityCodeValue;

    public final QStateCountyCodeValue cntyCode;

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QCntycityCodeValueId id;

    public QCntycityCodeValue(String variable) {
        this(CntycityCodeValue.class, forVariable(variable), INITS);
    }

    public QCntycityCodeValue(Path<? extends CntycityCodeValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCntycityCodeValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCntycityCodeValue(PathMetadata metadata, PathInits inits) {
        this(CntycityCodeValue.class, metadata, inits);
    }

    public QCntycityCodeValue(Class<? extends CntycityCodeValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cityCodeValue = inits.isInitialized("cityCodeValue") ? new QCityCodeValue(forProperty("cityCodeValue")) : null;
        this.cntyCode = inits.isInitialized("cntyCode") ? new QStateCountyCodeValue(forProperty("cntyCode")) : null;
        this.id = inits.isInitialized("id") ? new QCntycityCodeValueId(forProperty("id")) : null;
    }

}

