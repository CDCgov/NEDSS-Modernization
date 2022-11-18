package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueNumericHist is a Querydsl query type for ObsValueNumericHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueNumericHist extends EntityPathBase<ObsValueNumericHist> {

    private static final long serialVersionUID = 700722919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueNumericHist obsValueNumericHist = new QObsValueNumericHist("obsValueNumericHist");

    public final StringPath comparatorCd1 = createString("comparatorCd1");

    public final StringPath highRange = createString("highRange");

    public final QObsValueNumericHistId id;

    public final StringPath lowRange = createString("lowRange");

    public final NumberPath<Short> numericScale1 = createNumber("numericScale1", Short.class);

    public final NumberPath<Short> numericScale2 = createNumber("numericScale2", Short.class);

    public final StringPath numericUnitCd = createString("numericUnitCd");

    public final NumberPath<java.math.BigDecimal> numericValue1 = createNumber("numericValue1", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> numericValue2 = createNumber("numericValue2", java.math.BigDecimal.class);

    public final QObsValueNumeric obsValueNumeric;

    public final StringPath separatorCd = createString("separatorCd");

    public QObsValueNumericHist(String variable) {
        this(ObsValueNumericHist.class, forVariable(variable), INITS);
    }

    public QObsValueNumericHist(Path<? extends ObsValueNumericHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueNumericHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueNumericHist(PathMetadata metadata, PathInits inits) {
        this(ObsValueNumericHist.class, metadata, inits);
    }

    public QObsValueNumericHist(Class<? extends ObsValueNumericHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueNumericHistId(forProperty("id")) : null;
        this.obsValueNumeric = inits.isInitialized("obsValueNumeric") ? new QObsValueNumeric(forProperty("obsValueNumeric"), inits.get("obsValueNumeric")) : null;
    }

}

