package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActIdHistId is a Querydsl query type for ActIdHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActIdHistId extends BeanPath<ActIdHistId> {

    private static final long serialVersionUID = 1897475251L;

    public static final QActIdHistId actIdHistId = new QActIdHistId("actIdHistId");

    public final NumberPath<Short> actIdSeq = createNumber("actIdSeq", Short.class);

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QActIdHistId(String variable) {
        super(ActIdHistId.class, forVariable(variable));
    }

    public QActIdHistId(Path<? extends ActIdHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActIdHistId(PathMetadata metadata) {
        super(ActIdHistId.class, metadata);
    }

}

