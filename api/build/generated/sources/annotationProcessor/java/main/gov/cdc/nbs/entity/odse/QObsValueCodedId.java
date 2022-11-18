package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueCodedId is a Querydsl query type for ObsValueCodedId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueCodedId extends BeanPath<ObsValueCodedId> {

    private static final long serialVersionUID = 832752298L;

    public static final QObsValueCodedId obsValueCodedId = new QObsValueCodedId("obsValueCodedId");

    public final StringPath code = createString("code");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public QObsValueCodedId(String variable) {
        super(ObsValueCodedId.class, forVariable(variable));
    }

    public QObsValueCodedId(Path<? extends ObsValueCodedId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueCodedId(PathMetadata metadata) {
        super(ObsValueCodedId.class, metadata);
    }

}

