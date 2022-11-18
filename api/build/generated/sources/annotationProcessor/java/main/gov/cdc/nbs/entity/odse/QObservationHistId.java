package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObservationHistId is a Querydsl query type for ObservationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObservationHistId extends BeanPath<ObservationHistId> {

    private static final long serialVersionUID = 1144076690L;

    public static final QObservationHistId observationHistId = new QObservationHistId("observationHistId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObservationHistId(String variable) {
        super(ObservationHistId.class, forVariable(variable));
    }

    public QObservationHistId(Path<? extends ObservationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObservationHistId(PathMetadata metadata) {
        super(ObservationHistId.class, metadata);
    }

}

