package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostalLocatorHistId is a Querydsl query type for PostalLocatorHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostalLocatorHistId extends BeanPath<PostalLocatorHistId> {

    private static final long serialVersionUID = -2119408335L;

    public static final QPostalLocatorHistId postalLocatorHistId = new QPostalLocatorHistId("postalLocatorHistId");

    public final NumberPath<Long> postalLocatorUid = createNumber("postalLocatorUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPostalLocatorHistId(String variable) {
        super(PostalLocatorHistId.class, forVariable(variable));
    }

    public QPostalLocatorHistId(Path<? extends PostalLocatorHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostalLocatorHistId(PathMetadata metadata) {
        super(PostalLocatorHistId.class, metadata);
    }

}

