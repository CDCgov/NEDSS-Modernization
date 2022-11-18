package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueNumericHistId is a Querydsl query type for ObsValueNumericHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueNumericHistId extends BeanPath<ObsValueNumericHistId> {

    private static final long serialVersionUID = -915137950L;

    public static final QObsValueNumericHistId obsValueNumericHistId = new QObsValueNumericHistId("obsValueNumericHistId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueNumericSeq = createNumber("obsValueNumericSeq", Short.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObsValueNumericHistId(String variable) {
        super(ObsValueNumericHistId.class, forVariable(variable));
    }

    public QObsValueNumericHistId(Path<? extends ObsValueNumericHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueNumericHistId(PathMetadata metadata) {
        super(ObsValueNumericHistId.class, metadata);
    }

}

