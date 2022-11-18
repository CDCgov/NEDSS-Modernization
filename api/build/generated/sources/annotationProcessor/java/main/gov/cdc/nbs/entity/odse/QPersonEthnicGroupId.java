package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersonEthnicGroupId is a Querydsl query type for PersonEthnicGroupId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPersonEthnicGroupId extends BeanPath<PersonEthnicGroupId> {

    private static final long serialVersionUID = 1751424511L;

    public static final QPersonEthnicGroupId personEthnicGroupId = new QPersonEthnicGroupId("personEthnicGroupId");

    public final EnumPath<gov.cdc.nbs.entity.enums.Ethnicity> ethnicGroupCd = createEnum("ethnicGroupCd", gov.cdc.nbs.entity.enums.Ethnicity.class);

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public QPersonEthnicGroupId(String variable) {
        super(PersonEthnicGroupId.class, forVariable(variable));
    }

    public QPersonEthnicGroupId(Path<? extends PersonEthnicGroupId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersonEthnicGroupId(PathMetadata metadata) {
        super(PersonEthnicGroupId.class, metadata);
    }

}

