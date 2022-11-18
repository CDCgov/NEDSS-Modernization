package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPhysicalLocatorHistId is a Querydsl query type for PhysicalLocatorHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPhysicalLocatorHistId extends BeanPath<PhysicalLocatorHistId> {

    private static final long serialVersionUID = 494931557L;

    public static final QPhysicalLocatorHistId physicalLocatorHistId = new QPhysicalLocatorHistId("physicalLocatorHistId");

    public final NumberPath<Long> physicalLocatorUid = createNumber("physicalLocatorUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPhysicalLocatorHistId(String variable) {
        super(PhysicalLocatorHistId.class, forVariable(variable));
    }

    public QPhysicalLocatorHistId(Path<? extends PhysicalLocatorHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhysicalLocatorHistId(PathMetadata metadata) {
        super(PhysicalLocatorHistId.class, metadata);
    }

}

