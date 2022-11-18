package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsUiComponent is a Querydsl query type for NbsUiComponent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsUiComponent extends EntityPathBase<NbsUiComponent> {

    private static final long serialVersionUID = -358315967L;

    public static final QNbsUiComponent nbsUiComponent = new QNbsUiComponent("nbsUiComponent");

    public final StringPath componentBehavior = createString("componentBehavior");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ldfAvailableInd = createString("ldfAvailableInd");

    public final StringPath typeCd = createString("typeCd");

    public final StringPath typeCdDesc = createString("typeCdDesc");

    public QNbsUiComponent(String variable) {
        super(NbsUiComponent.class, forVariable(variable));
    }

    public QNbsUiComponent(Path<? extends NbsUiComponent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsUiComponent(PathMetadata metadata) {
        super(NbsUiComponent.class, metadata);
    }

}

