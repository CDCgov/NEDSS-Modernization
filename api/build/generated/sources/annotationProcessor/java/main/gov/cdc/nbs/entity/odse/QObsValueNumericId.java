package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueNumericId is a Querydsl query type for ObsValueNumericId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueNumericId extends BeanPath<ObsValueNumericId> {

    private static final long serialVersionUID = -12678624L;

    public static final QObsValueNumericId obsValueNumericId = new QObsValueNumericId("obsValueNumericId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueNumericSeq = createNumber("obsValueNumericSeq", Short.class);

    public QObsValueNumericId(String variable) {
        super(ObsValueNumericId.class, forVariable(variable));
    }

    public QObsValueNumericId(Path<? extends ObsValueNumericId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueNumericId(PathMetadata metadata) {
        super(ObsValueNumericId.class, metadata);
    }

}

