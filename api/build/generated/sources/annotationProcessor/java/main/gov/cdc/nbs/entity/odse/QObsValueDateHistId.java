package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueDateHistId is a Querydsl query type for ObsValueDateHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueDateHistId extends BeanPath<ObsValueDateHistId> {

    private static final long serialVersionUID = 1474311955L;

    public static final QObsValueDateHistId obsValueDateHistId = new QObsValueDateHistId("obsValueDateHistId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueDateSeq = createNumber("obsValueDateSeq", Short.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObsValueDateHistId(String variable) {
        super(ObsValueDateHistId.class, forVariable(variable));
    }

    public QObsValueDateHistId(Path<? extends ObsValueDateHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueDateHistId(PathMetadata metadata) {
        super(ObsValueDateHistId.class, metadata);
    }

}

