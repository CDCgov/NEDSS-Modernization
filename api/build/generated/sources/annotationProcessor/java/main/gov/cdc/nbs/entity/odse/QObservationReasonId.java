package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObservationReasonId is a Querydsl query type for ObservationReasonId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObservationReasonId extends BeanPath<ObservationReasonId> {

    private static final long serialVersionUID = 424153844L;

    public static final QObservationReasonId observationReasonId = new QObservationReasonId("observationReasonId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final StringPath reasonCd = createString("reasonCd");

    public QObservationReasonId(String variable) {
        super(ObservationReasonId.class, forVariable(variable));
    }

    public QObservationReasonId(Path<? extends ObservationReasonId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObservationReasonId(PathMetadata metadata) {
        super(ObservationReasonId.class, metadata);
    }

}

