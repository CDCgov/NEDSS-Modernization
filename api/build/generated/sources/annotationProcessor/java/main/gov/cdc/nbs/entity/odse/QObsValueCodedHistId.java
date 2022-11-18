package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueCodedHistId is a Querydsl query type for ObsValueCodedHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueCodedHistId extends BeanPath<ObsValueCodedHistId> {

    private static final long serialVersionUID = -1219426836L;

    public static final QObsValueCodedHistId obsValueCodedHistId = new QObsValueCodedHistId("obsValueCodedHistId");

    public final StringPath code = createString("code");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObsValueCodedHistId(String variable) {
        super(ObsValueCodedHistId.class, forVariable(variable));
    }

    public QObsValueCodedHistId(Path<? extends ObsValueCodedHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueCodedHistId(PathMetadata metadata) {
        super(ObsValueCodedHistId.class, metadata);
    }

}

