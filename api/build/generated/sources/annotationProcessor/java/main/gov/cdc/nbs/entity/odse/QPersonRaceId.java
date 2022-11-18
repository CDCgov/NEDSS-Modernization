package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonRaceId is a Querydsl query type for PersonRaceId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonRaceId extends BeanPath<PersonRaceId> {

    private static final long serialVersionUID = -495248520L;

    public static final QPersonRaceId personRaceId = new QPersonRaceId("personRaceId");

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final EnumPath<gov.cdc.nbs.entity.enums.Race> raceCd = createEnum("raceCd", gov.cdc.nbs.entity.enums.Race.class);

    public QPersonRaceId(String variable) {
        super(PersonRaceId.class, forVariable(variable));
    }

    public QPersonRaceId(Path<? extends PersonRaceId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonRaceId(PathMetadata metadata) {
        super(PersonRaceId.class, metadata);
    }

}

