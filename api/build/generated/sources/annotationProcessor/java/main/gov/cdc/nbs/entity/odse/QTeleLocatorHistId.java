package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeleLocatorHistId is a Querydsl query type for TeleLocatorHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QTeleLocatorHistId extends BeanPath<TeleLocatorHistId> {

    private static final long serialVersionUID = -1530300686L;

    public static final QTeleLocatorHistId teleLocatorHistId = new QTeleLocatorHistId("teleLocatorHistId");

    public final NumberPath<Long> teleLocatorUid = createNumber("teleLocatorUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QTeleLocatorHistId(String variable) {
        super(TeleLocatorHistId.class, forVariable(variable));
    }

    public QTeleLocatorHistId(Path<? extends TeleLocatorHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeleLocatorHistId(PathMetadata metadata) {
        super(TeleLocatorHistId.class, metadata);
    }

}

