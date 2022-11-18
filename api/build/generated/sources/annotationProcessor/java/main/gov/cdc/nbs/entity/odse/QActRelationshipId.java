package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActRelationshipId is a Querydsl query type for ActRelationshipId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActRelationshipId extends BeanPath<ActRelationshipId> {

    private static final long serialVersionUID = 848579854L;

    public static final QActRelationshipId actRelationshipId = new QActRelationshipId("actRelationshipId");

    public final NumberPath<Long> sourceActUid = createNumber("sourceActUid", Long.class);

    public final NumberPath<Long> targetActUid = createNumber("targetActUid", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public QActRelationshipId(String variable) {
        super(ActRelationshipId.class, forVariable(variable));
    }

    public QActRelationshipId(Path<? extends ActRelationshipId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActRelationshipId(PathMetadata metadata) {
        super(ActRelationshipId.class, metadata);
    }

}

