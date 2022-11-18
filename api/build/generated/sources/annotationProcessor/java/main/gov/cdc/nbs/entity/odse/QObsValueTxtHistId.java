package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueTxtHistId is a Querydsl query type for ObsValueTxtHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueTxtHistId extends BeanPath<ObsValueTxtHistId> {

    private static final long serialVersionUID = -254691451L;

    public static final QObsValueTxtHistId obsValueTxtHistId = new QObsValueTxtHistId("obsValueTxtHistId");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public final NumberPath<Short> obsValueTxtSeq = createNumber("obsValueTxtSeq", Short.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QObsValueTxtHistId(String variable) {
        super(ObsValueTxtHistId.class, forVariable(variable));
    }

    public QObsValueTxtHistId(Path<? extends ObsValueTxtHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueTxtHistId(PathMetadata metadata) {
        super(ObsValueTxtHistId.class, metadata);
    }

}

