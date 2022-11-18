package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubstanceAdministrationHist is a Querydsl query type for SubstanceAdministrationHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubstanceAdministrationHist extends EntityPathBase<SubstanceAdministrationHist> {

    private static final long serialVersionUID = 2040654169L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubstanceAdministrationHist substanceAdministrationHist = new QSubstanceAdministrationHist("substanceAdministrationHist");

    public final StringPath doseQty = createString("doseQty");

    public final StringPath doseQtyUnitCd = createString("doseQtyUnitCd");

    public final StringPath formCd = createString("formCd");

    public final StringPath formDescTxt = createString("formDescTxt");

    public final QSubstanceAdministrationHistId id;

    public final QSubstanceAdministration interventionUid;

    public final StringPath rateQty = createString("rateQty");

    public final StringPath rateQtyUnitCd = createString("rateQtyUnitCd");

    public final StringPath routeCd = createString("routeCd");

    public final StringPath routeDescTxt = createString("routeDescTxt");

    public QSubstanceAdministrationHist(String variable) {
        this(SubstanceAdministrationHist.class, forVariable(variable), INITS);
    }

    public QSubstanceAdministrationHist(Path<? extends SubstanceAdministrationHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubstanceAdministrationHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubstanceAdministrationHist(PathMetadata metadata, PathInits inits) {
        this(SubstanceAdministrationHist.class, metadata, inits);
    }

    public QSubstanceAdministrationHist(Class<? extends SubstanceAdministrationHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QSubstanceAdministrationHistId(forProperty("id")) : null;
        this.interventionUid = inits.isInitialized("interventionUid") ? new QSubstanceAdministration(forProperty("interventionUid"), inits.get("interventionUid")) : null;
    }

}

