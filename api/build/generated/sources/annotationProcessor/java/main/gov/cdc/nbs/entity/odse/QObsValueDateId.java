package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueDateId is a Querydsl query type for ObsValueDateId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueDateId extends BeanPath<ObsValueDateId> {

    private static final long serialVersionUID = -95508655L;

    public static final QObsValueDateId obsValueDateId = new QObsValueDateId("obsValueDateId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueDateSeq = createNumber("obsValueDateSeq", Short.class);

    public QObsValueDateId(String variable) {
        super(ObsValueDateId.class, forVariable(variable));
    }

    public QObsValueDateId(Path<? extends ObsValueDateId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueDateId(PathMetadata metadata) {
        super(ObsValueDateId.class, metadata);
    }

}

