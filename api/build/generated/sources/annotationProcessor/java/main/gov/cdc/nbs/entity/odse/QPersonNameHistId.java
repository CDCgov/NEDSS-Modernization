package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonNameHistId is a Querydsl query type for PersonNameHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonNameHistId extends BeanPath<PersonNameHistId> {

    private static final long serialVersionUID = -327271436L;

    public static final QPersonNameHistId personNameHistId = new QPersonNameHistId("personNameHistId");

    public final NumberPath<Short> personNameSeq = createNumber("personNameSeq", Short.class);

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPersonNameHistId(String variable) {
        super(PersonNameHistId.class, forVariable(variable));
    }

    public QPersonNameHistId(Path<? extends PersonNameHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonNameHistId(PathMetadata metadata) {
        super(PersonNameHistId.class, metadata);
    }

}

