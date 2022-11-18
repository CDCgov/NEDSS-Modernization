package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGeocodingResultHistId is a Querydsl query type for GeocodingResultHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGeocodingResultHistId extends BeanPath<GeocodingResultHistId> {

    private static final long serialVersionUID = 1638570462L;

    public static final QGeocodingResultHistId geocodingResultHistId = new QGeocodingResultHistId("geocodingResultHistId");

    public final NumberPath<Long> geocodingResultUid = createNumber("geocodingResultUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QGeocodingResultHistId(String variable) {
        super(GeocodingResultHistId.class, forVariable(variable));
    }

    public QGeocodingResultHistId(Path<? extends GeocodingResultHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGeocodingResultHistId(PathMetadata metadata) {
        super(GeocodingResultHistId.class, metadata);
    }

}

