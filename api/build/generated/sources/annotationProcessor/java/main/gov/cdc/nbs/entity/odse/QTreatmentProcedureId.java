package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTreatmentProcedureId is a Querydsl query type for TreatmentProcedureId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTreatmentProcedureId extends BeanPath<TreatmentProcedureId> {

    private static final long serialVersionUID = -853199699L;

    public static final QTreatmentProcedureId treatmentProcedureId = new QTreatmentProcedureId("treatmentProcedureId");

    public final NumberPath<Short> treatmentProcedureSeq = createNumber("treatmentProcedureSeq", Short.class);

    public final NumberPath<Long> treatmentUid = createNumber("treatmentUid", Long.class);

    public QTreatmentProcedureId(String variable) {
        super(TreatmentProcedureId.class, forVariable(variable));
    }

    public QTreatmentProcedureId(Path<? extends TreatmentProcedureId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTreatmentProcedureId(PathMetadata metadata) {
        super(TreatmentProcedureId.class, metadata);
    }

}

