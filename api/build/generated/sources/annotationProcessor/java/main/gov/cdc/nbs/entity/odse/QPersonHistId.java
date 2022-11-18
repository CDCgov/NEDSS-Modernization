package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonHistId is a Querydsl query type for PersonHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonHistId extends BeanPath<PersonHistId> {

    private static final long serialVersionUID = -773660791L;

    public static final QPersonHistId personHistId = new QPersonHistId("personHistId");

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPersonHistId(String variable) {
        super(PersonHistId.class, forVariable(variable));
    }

    public QPersonHistId(Path<? extends PersonHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonHistId(PathMetadata metadata) {
        super(PersonHistId.class, metadata);
    }

}

