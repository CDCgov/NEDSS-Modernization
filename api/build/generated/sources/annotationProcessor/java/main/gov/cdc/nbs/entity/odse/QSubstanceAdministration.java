package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubstanceAdministration is a Querydsl query type for SubstanceAdministration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubstanceAdministration extends EntityPathBase<SubstanceAdministration> {

    private static final long serialVersionUID = 1744058775L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubstanceAdministration substanceAdministration = new QSubstanceAdministration("substanceAdministration");

    public final StringPath doseQty = createString("doseQty");

    public final StringPath doseQtyUnitCd = createString("doseQtyUnitCd");

    public final StringPath formCd = createString("formCd");

    public final StringPath formDescTxt = createString("formDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIntervention intervention;

    public final StringPath rateQty = createString("rateQty");

    public final StringPath rateQtyUnitCd = createString("rateQtyUnitCd");

    public final StringPath routeCd = createString("routeCd");

    public final StringPath routeDescTxt = createString("routeDescTxt");

    public QSubstanceAdministration(String variable) {
        this(SubstanceAdministration.class, forVariable(variable), INITS);
    }

    public QSubstanceAdministration(Path<? extends SubstanceAdministration> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubstanceAdministration(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubstanceAdministration(PathMetadata metadata, PathInits inits) {
        this(SubstanceAdministration.class, metadata, inits);
    }

    public QSubstanceAdministration(Class<? extends SubstanceAdministration> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.intervention = inits.isInitialized("intervention") ? new QIntervention(forProperty("intervention"), inits.get("intervention")) : null;
    }

}

