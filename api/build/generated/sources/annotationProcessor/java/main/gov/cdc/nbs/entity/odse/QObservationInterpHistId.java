package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObservationInterpHistId is a Querydsl query type for ObservationInterpHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObservationInterpHistId extends BeanPath<ObservationInterpHistId> {

    private static final long serialVersionUID = -585768442L;

    public static final QObservationInterpHistId observationInterpHistId = new QObservationInterpHistId("observationInterpHistId");

    public final StringPath interpretationCd = createString("interpretationCd");

    public final StringPath interpretationTxt = createString("interpretationTxt");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObservationInterpHistId(String variable) {
        super(ObservationInterpHistId.class, forVariable(variable));
    }

    public QObservationInterpHistId(Path<? extends ObservationInterpHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObservationInterpHistId(PathMetadata metadata) {
        super(ObservationInterpHistId.class, metadata);
    }

}

