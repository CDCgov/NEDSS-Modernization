package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStateDefinedFieldDataHistId is a Querydsl query type for StateDefinedFieldDataHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStateDefinedFieldDataHistId extends BeanPath<StateDefinedFieldDataHistId> {

    private static final long serialVersionUID = 104643730L;

    public static final QStateDefinedFieldDataHistId stateDefinedFieldDataHistId = new QStateDefinedFieldDataHistId("stateDefinedFieldDataHistId");

    public final NumberPath<Long> businessObjectUid = createNumber("businessObjectUid", Long.class);

    public final NumberPath<Long> ldfUid = createNumber("ldfUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QStateDefinedFieldDataHistId(String variable) {
        super(StateDefinedFieldDataHistId.class, forVariable(variable));
    }

    public QStateDefinedFieldDataHistId(Path<? extends StateDefinedFieldDataHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStateDefinedFieldDataHistId(PathMetadata metadata) {
        super(StateDefinedFieldDataHistId.class, metadata);
    }

}

