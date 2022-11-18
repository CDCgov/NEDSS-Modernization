package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentProcedureHistId is a Querydsl query type for TreatmentProcedureHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTreatmentProcedureHistId extends BeanPath<TreatmentProcedureHistId> {

    private static final long serialVersionUID = -1749502353L;

    public static final QTreatmentProcedureHistId treatmentProcedureHistId = new QTreatmentProcedureHistId("treatmentProcedureHistId");

    public final NumberPath<Short> treatmentProcedureSeq = createNumber("treatmentProcedureSeq", Short.class);

    public final NumberPath<Long> treatmentUid = createNumber("treatmentUid", Long.class);

    public final NumberPath<Short> versionCtlrNbr = createNumber("versionCtlrNbr", Short.class);

    public QTreatmentProcedureHistId(String variable) {
        super(TreatmentProcedureHistId.class, forVariable(variable));
    }

    public QTreatmentProcedureHistId(Path<? extends TreatmentProcedureHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentProcedureHistId(PathMetadata metadata) {
        super(TreatmentProcedureHistId.class, metadata);
    }

}

