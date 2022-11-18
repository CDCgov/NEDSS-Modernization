package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentAdministeredHistId is a Querydsl query type for TreatmentAdministeredHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTreatmentAdministeredHistId extends BeanPath<TreatmentAdministeredHistId> {

    private static final long serialVersionUID = 1863318501L;

    public static final QTreatmentAdministeredHistId treatmentAdministeredHistId = new QTreatmentAdministeredHistId("treatmentAdministeredHistId");

    public final NumberPath<Short> treatmentAdministeredSeq = createNumber("treatmentAdministeredSeq", Short.class);

    public final NumberPath<Long> treatmentUid = createNumber("treatmentUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QTreatmentAdministeredHistId(String variable) {
        super(TreatmentAdministeredHistId.class, forVariable(variable));
    }

    public QTreatmentAdministeredHistId(Path<? extends TreatmentAdministeredHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentAdministeredHistId(PathMetadata metadata) {
        super(TreatmentAdministeredHistId.class, metadata);
    }

}

