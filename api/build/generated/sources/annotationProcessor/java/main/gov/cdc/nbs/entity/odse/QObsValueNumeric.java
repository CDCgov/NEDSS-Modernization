package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueNumeric is a Querydsl query type for ObsValueNumeric
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueNumeric extends EntityPathBase<ObsValueNumeric> {

    private static final long serialVersionUID = -2109508059L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueNumeric obsValueNumeric = new QObsValueNumeric("obsValueNumeric");

    public final StringPath comparatorCd1 = createString("comparatorCd1");

    public final StringPath highRange = createString("highRange");

    public final QObsValueNumericId id;

    public final StringPath lowRange = createString("lowRange");

    public final NumberPath<Short> numericScale1 = createNumber("numericScale1", Short.class);

    public final NumberPath<Short> numericScale2 = createNumber("numericScale2", Short.class);

    public final StringPath numericUnitCd = createString("numericUnitCd");

    public final NumberPath<java.math.BigDecimal> numericValue1 = createNumber("numericValue1", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> numericValue2 = createNumber("numericValue2", java.math.BigDecimal.class);

    public final QObservation observationUid;

    public final StringPath separatorCd = createString("separatorCd");

    public QObsValueNumeric(String variable) {
        this(ObsValueNumeric.class, forVariable(variable), INITS);
    }

    public QObsValueNumeric(Path<? extends ObsValueNumeric> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueNumeric(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueNumeric(PathMetadata metadata, PathInits inits) {
        this(ObsValueNumeric.class, metadata, inits);
    }

    public QObsValueNumeric(Class<? extends ObsValueNumeric> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueNumericId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

