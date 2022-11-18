package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueTxtId is a Querydsl query type for ObsValueTxtId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueTxtId extends BeanPath<ObsValueTxtId> {

    private static final long serialVersionUID = -1511640893L;

    public static final QObsValueTxtId obsValueTxtId = new QObsValueTxtId("obsValueTxtId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueTxtSeq = createNumber("obsValueTxtSeq", Short.class);

    public QObsValueTxtId(String variable) {
        super(ObsValueTxtId.class, forVariable(variable));
    }

    public QObsValueTxtId(Path<? extends ObsValueTxtId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueTxtId(PathMetadata metadata) {
        super(ObsValueTxtId.class, metadata);
    }

}

