package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActRelationshipHistId is a Querydsl query type for ActRelationshipHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActRelationshipHistId extends BeanPath<ActRelationshipHistId> {

    private static final long serialVersionUID = 87209552L;

    public static final QActRelationshipHistId actRelationshipHistId = new QActRelationshipHistId("actRelationshipHistId");

    public final NumberPath<Long> sourceActUid = createNumber("sourceActUid", Long.class);

    public final NumberPath<Long> targetActUid = createNumber("targetActUid", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QActRelationshipHistId(String variable) {
        super(ActRelationshipHistId.class, forVariable(variable));
    }

    public QActRelationshipHistId(Path<? extends ActRelationshipHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActRelationshipHistId(PathMetadata metadata) {
        super(ActRelationshipHistId.class, metadata);
    }

}

