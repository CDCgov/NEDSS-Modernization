package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInterventionHistId is a Querydsl query type for InterventionHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QInterventionHistId extends BeanPath<InterventionHistId> {

    private static final long serialVersionUID = 3925067L;

    public static final QInterventionHistId interventionHistId = new QInterventionHistId("interventionHistId");

    public final NumberPath<Long> interventionUid = createNumber("interventionUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QInterventionHistId(String variable) {
        super(InterventionHistId.class, forVariable(variable));
    }

    public QInterventionHistId(Path<? extends InterventionHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInterventionHistId(PathMetadata metadata) {
        super(InterventionHistId.class, metadata);
    }

}

