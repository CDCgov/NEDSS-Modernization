package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObservationReasonHistId is a Querydsl query type for ObservationReasonHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObservationReasonHistId extends BeanPath<ObservationReasonHistId> {

    private static final long serialVersionUID = 1059395894L;

    public static final QObservationReasonHistId observationReasonHistId = new QObservationReasonHistId("observationReasonHistId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final StringPath reasonCd = createString("reasonCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObservationReasonHistId(String variable) {
        super(ObservationReasonHistId.class, forVariable(variable));
    }

    public QObservationReasonHistId(Path<? extends ObservationReasonHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObservationReasonHistId(PathMetadata metadata) {
        super(ObservationReasonHistId.class, metadata);
    }

}

