package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStateModelId is a Querydsl query type for StateModelId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStateModelId extends BeanPath<StateModelId> {

    private static final long serialVersionUID = 1376117953L;

    public static final QStateModelId stateModelId = new QStateModelId("stateModelId");

    public final StringPath businessTriggerCode = createString("businessTriggerCode");

    public final StringPath businessTriggerCodeSetNm = createString("businessTriggerCodeSetNm");

    public final NumberPath<Short> businessTriggerSetSeqNum = createNumber("businessTriggerSetSeqNum", Short.class);

    public final StringPath moduleCd = createString("moduleCd");

    public final StringPath recordStatusFromCode = createString("recordStatusFromCode");

    public QStateModelId(String variable) {
        super(StateModelId.class, forVariable(variable));
    }

    public QStateModelId(Path<? extends StateModelId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStateModelId(PathMetadata metadata) {
        super(StateModelId.class, metadata);
    }

}

