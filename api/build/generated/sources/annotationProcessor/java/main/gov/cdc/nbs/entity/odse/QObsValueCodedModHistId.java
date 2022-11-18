package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueCodedModHistId is a Querydsl query type for ObsValueCodedModHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueCodedModHistId extends BeanPath<ObsValueCodedModHistId> {

    private static final long serialVersionUID = -1349114608L;

    public static final QObsValueCodedModHistId obsValueCodedModHistId = new QObsValueCodedModHistId("obsValueCodedModHistId");

    public final StringPath code = createString("code");

    public final StringPath codeModCd = createString("codeModCd");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObsValueCodedModHistId(String variable) {
        super(ObsValueCodedModHistId.class, forVariable(variable));
    }

    public QObsValueCodedModHistId(Path<? extends ObsValueCodedModHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueCodedModHistId(PathMetadata metadata) {
        super(ObsValueCodedModHistId.class, metadata);
    }

}

