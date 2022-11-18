package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentAdministeredId is a Querydsl query type for TreatmentAdministeredId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTreatmentAdministeredId extends BeanPath<TreatmentAdministeredId> {

    private static final long serialVersionUID = 1001760035L;

    public static final QTreatmentAdministeredId treatmentAdministeredId = new QTreatmentAdministeredId("treatmentAdministeredId");

    public final NumberPath<Short> treatmentAdministeredSeq = createNumber("treatmentAdministeredSeq", Short.class);

    public final NumberPath<Long> treatmentUid = createNumber("treatmentUid", Long.class);

    public QTreatmentAdministeredId(String variable) {
        super(TreatmentAdministeredId.class, forVariable(variable));
    }

    public QTreatmentAdministeredId(Path<? extends TreatmentAdministeredId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentAdministeredId(PathMetadata metadata) {
        super(TreatmentAdministeredId.class, metadata);
    }

}

