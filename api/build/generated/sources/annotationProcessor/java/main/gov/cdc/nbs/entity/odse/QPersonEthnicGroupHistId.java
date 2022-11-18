package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonEthnicGroupHistId is a Querydsl query type for PersonEthnicGroupHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonEthnicGroupHistId extends BeanPath<PersonEthnicGroupHistId> {

    private static final long serialVersionUID = 1201612481L;

    public static final QPersonEthnicGroupHistId personEthnicGroupHistId = new QPersonEthnicGroupHistId("personEthnicGroupHistId");

    public final StringPath ethnicGroupCd = createString("ethnicGroupCd");

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPersonEthnicGroupHistId(String variable) {
        super(PersonEthnicGroupHistId.class, forVariable(variable));
    }

    public QPersonEthnicGroupHistId(Path<? extends PersonEthnicGroupHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonEthnicGroupHistId(PathMetadata metadata) {
        super(PersonEthnicGroupHistId.class, metadata);
    }

}

