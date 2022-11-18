package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonMergeId is a Querydsl query type for PersonMergeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonMergeId extends BeanPath<PersonMergeId> {

    private static final long serialVersionUID = 1813069127L;

    public static final QPersonMergeId personMergeId = new QPersonMergeId("personMergeId");

    public final NumberPath<Short> supercededVersionCtrlNbr = createNumber("supercededVersionCtrlNbr", Short.class);

    public final NumberPath<Long> supercedPersonUid = createNumber("supercedPersonUid", Long.class);

    public final NumberPath<Long> survivingPersonUid = createNumber("survivingPersonUid", Long.class);

    public QPersonMergeId(String variable) {
        super(PersonMergeId.class, forVariable(variable));
    }

    public QPersonMergeId(Path<? extends PersonMergeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonMergeId(PathMetadata metadata) {
        super(PersonMergeId.class, metadata);
    }

}

