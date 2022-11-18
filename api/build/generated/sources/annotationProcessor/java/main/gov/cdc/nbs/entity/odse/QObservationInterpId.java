package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObservationInterpId is a Querydsl query type for ObservationInterpId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObservationInterpId extends BeanPath<ObservationInterpId> {

    private static final long serialVersionUID = 1847444420L;

    public static final QObservationInterpId observationInterpId = new QObservationInterpId("observationInterpId");

    public final StringPath interpretationCd = createString("interpretationCd");

    public final StringPath interpretationDescTxt = createString("interpretationDescTxt");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public QObservationInterpId(String variable) {
        super(ObservationInterpId.class, forVariable(variable));
    }

    public QObservationInterpId(Path<? extends ObservationInterpId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObservationInterpId(PathMetadata metadata) {
        super(ObservationInterpId.class, metadata);
    }

}

