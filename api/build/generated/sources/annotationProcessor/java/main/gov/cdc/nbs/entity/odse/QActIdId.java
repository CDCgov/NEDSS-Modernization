package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActIdId is a Querydsl query type for ActIdId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActIdId extends BeanPath<ActIdId> {

    private static final long serialVersionUID = 2091085041L;

    public static final QActIdId actIdId = new QActIdId("actIdId");

    public final NumberPath<Short> actIdSeq = createNumber("actIdSeq", Short.class);

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public QActIdId(String variable) {
        super(ActIdId.class, forVariable(variable));
    }

    public QActIdId(Path<? extends ActIdId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActIdId(PathMetadata metadata) {
        super(ActIdId.class, metadata);
    }

}

