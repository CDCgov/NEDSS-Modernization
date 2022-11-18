package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStateDefinedFieldDatumId is a Querydsl query type for StateDefinedFieldDatumId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStateDefinedFieldDatumId extends BeanPath<StateDefinedFieldDatumId> {

    private static final long serialVersionUID = -109944481L;

    public static final QStateDefinedFieldDatumId stateDefinedFieldDatumId = new QStateDefinedFieldDatumId("stateDefinedFieldDatumId");

    public final NumberPath<Long> businessObjectUid = createNumber("businessObjectUid", Long.class);

    public final NumberPath<Long> ldfUid = createNumber("ldfUid", Long.class);

    public QStateDefinedFieldDatumId(String variable) {
        super(StateDefinedFieldDatumId.class, forVariable(variable));
    }

    public QStateDefinedFieldDatumId(Path<? extends StateDefinedFieldDatumId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStateDefinedFieldDatumId(PathMetadata metadata) {
        super(StateDefinedFieldDatumId.class, metadata);
    }

}

