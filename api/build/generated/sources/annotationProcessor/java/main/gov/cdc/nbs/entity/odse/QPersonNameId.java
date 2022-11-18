package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonNameId is a Querydsl query type for PersonNameId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonNameId extends BeanPath<PersonNameId> {

    private static final long serialVersionUID = -609467214L;

    public static final QPersonNameId personNameId = new QPersonNameId("personNameId");

    public final NumberPath<Short> personNameSeq = createNumber("personNameSeq", Short.class);

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public QPersonNameId(String variable) {
        super(PersonNameId.class, forVariable(variable));
    }

    public QPersonNameId(Path<? extends PersonNameId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonNameId(PathMetadata metadata) {
        super(PersonNameId.class, metadata);
    }

}

