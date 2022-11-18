package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsConversionCondition is a Querydsl query type for NbsConversionCondition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConversionCondition extends EntityPathBase<NbsConversionCondition> {

    private static final long serialVersionUID = 1382997021L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsConversionCondition nbsConversionCondition = new QNbsConversionCondition("nbsConversionCondition");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath conditionCd = createString("conditionCd");

    public final NumberPath<Long> conditionCdGroupId = createNumber("conditionCdGroupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final QNbsConversionPageMgmt nbsConversionPageMgmtUid;

    public final StringPath statusCd = createString("statusCd");

    public QNbsConversionCondition(String variable) {
        this(NbsConversionCondition.class, forVariable(variable), INITS);
    }

    public QNbsConversionCondition(Path<? extends NbsConversionCondition> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsConversionCondition(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsConversionCondition(PathMetadata metadata, PathInits inits) {
        this(NbsConversionCondition.class, metadata, inits);
    }

    public QNbsConversionCondition(Class<? extends NbsConversionCondition> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsConversionPageMgmtUid = inits.isInitialized("nbsConversionPageMgmtUid") ? new QNbsConversionPageMgmt(forProperty("nbsConversionPageMgmtUid")) : null;
    }

}

