package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonRaceHistId is a Querydsl query type for PersonRaceHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonRaceHistId extends BeanPath<PersonRaceHistId> {

    private static final long serialVersionUID = -1361559622L;

    public static final QPersonRaceHistId personRaceHistId = new QPersonRaceHistId("personRaceHistId");

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final StringPath raceCd = createString("raceCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPersonRaceHistId(String variable) {
        super(PersonRaceHistId.class, forVariable(variable));
    }

    public QPersonRaceHistId(Path<? extends PersonRaceHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonRaceHistId(PathMetadata metadata) {
        super(PersonRaceHistId.class, metadata);
    }

}

