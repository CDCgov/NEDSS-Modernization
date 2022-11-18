package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsConversionMaster is a Querydsl query type for NbsConversionMaster
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConversionMaster extends EntityPathBase<NbsConversionMaster> {

    private static final long serialVersionUID = -60036448L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsConversionMaster nbsConversionMaster = new QNbsConversionMaster("nbsConversionMaster");

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final DateTimePath<java.time.Instant> endTime = createDateTime("endTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNbsConversionCondition nbsConversionConditionUid;

    public final StringPath processMessageTxt = createString("processMessageTxt");

    public final StringPath processTypeInd = createString("processTypeInd");

    public final DateTimePath<java.time.Instant> startTime = createDateTime("startTime", java.time.Instant.class);

    public final StringPath statusCd = createString("statusCd");

    public QNbsConversionMaster(String variable) {
        this(NbsConversionMaster.class, forVariable(variable), INITS);
    }

    public QNbsConversionMaster(Path<? extends NbsConversionMaster> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsConversionMaster(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsConversionMaster(PathMetadata metadata, PathInits inits) {
        this(NbsConversionMaster.class, metadata, inits);
    }

    public QNbsConversionMaster(Class<? extends NbsConversionMaster> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsConversionConditionUid = inits.isInitialized("nbsConversionConditionUid") ? new QNbsConversionCondition(forProperty("nbsConversionConditionUid"), inits.get("nbsConversionConditionUid")) : null;
    }

}

