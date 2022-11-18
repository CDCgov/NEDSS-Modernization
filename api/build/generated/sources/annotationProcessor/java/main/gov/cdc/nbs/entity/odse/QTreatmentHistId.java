package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentHistId is a Querydsl query type for TreatmentHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTreatmentHistId extends BeanPath<TreatmentHistId> {

    private static final long serialVersionUID = -1678248162L;

    public static final QTreatmentHistId treatmentHistId = new QTreatmentHistId("treatmentHistId");

    public final NumberPath<Long> treatmentUid = createNumber("treatmentUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QTreatmentHistId(String variable) {
        super(TreatmentHistId.class, forVariable(variable));
    }

    public QTreatmentHistId(Path<? extends TreatmentHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentHistId(PathMetadata metadata) {
        super(TreatmentHistId.class, metadata);
    }

}

