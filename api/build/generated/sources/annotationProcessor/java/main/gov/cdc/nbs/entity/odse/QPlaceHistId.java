package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlaceHistId is a Querydsl query type for PlaceHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPlaceHistId extends BeanPath<PlaceHistId> {

    private static final long serialVersionUID = -2117784339L;

    public static final QPlaceHistId placeHistId = new QPlaceHistId("placeHistId");

    public final NumberPath<Long> placeUid = createNumber("placeUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPlaceHistId(String variable) {
        super(PlaceHistId.class, forVariable(variable));
    }

    public QPlaceHistId(Path<? extends PlaceHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlaceHistId(PathMetadata metadata) {
        super(PlaceHistId.class, metadata);
    }

}

