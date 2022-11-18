package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomSubformMetadatum is a Querydsl query type for CustomSubformMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomSubformMetadatum extends EntityPathBase<CustomSubformMetadatum> {

    private static final long serialVersionUID = -5943378L;

    public static final QCustomSubformMetadatum customSubformMetadatum = new QCustomSubformMetadatum("customSubformMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final StringPath adminComment = createString("adminComment");

    public final StringPath businessObjectNm = createString("businessObjectNm");

    public final StringPath classCd = createString("classCd");

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath conditionDescTxt = createString("conditionDescTxt");

    public final StringPath deploymentCd = createString("deploymentCd");

    public final NumberPath<Integer> displayOrderNbr = createNumber("displayOrderNbr", Integer.class);

    public final StringPath htmlData = createString("htmlData");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> importVersionNbr = createNumber("importVersionNbr", Long.class);

    public final StringPath pageSetId = createString("pageSetId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath stateCd = createString("stateCd");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final StringPath subformNm = createString("subformNm");

    public final StringPath subformOid = createString("subformOid");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QCustomSubformMetadatum(String variable) {
        super(CustomSubformMetadatum.class, forVariable(variable));
    }

    public QCustomSubformMetadatum(Path<? extends CustomSubformMetadatum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomSubformMetadatum(PathMetadata metadata) {
        super(CustomSubformMetadatum.class, metadata);
    }

}

