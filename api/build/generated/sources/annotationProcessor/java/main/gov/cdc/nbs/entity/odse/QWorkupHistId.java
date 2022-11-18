package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWorkupHistId is a Querydsl query type for WorkupHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QWorkupHistId extends BeanPath<WorkupHistId> {

    private static final long serialVersionUID = 261975136L;

    public static final QWorkupHistId workupHistId = new QWorkupHistId("workupHistId");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public final NumberPath<Long> workupUid = createNumber("workupUid", Long.class);

    public QWorkupHistId(String variable) {
        super(WorkupHistId.class, forVariable(variable));
    }

    public QWorkupHistId(Path<? extends WorkupHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkupHistId(PathMetadata metadata) {
        super(WorkupHistId.class, metadata);
    }

}

