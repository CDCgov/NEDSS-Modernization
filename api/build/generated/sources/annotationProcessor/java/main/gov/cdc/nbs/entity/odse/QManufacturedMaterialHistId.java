package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturedMaterialHistId is a Querydsl query type for ManufacturedMaterialHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QManufacturedMaterialHistId extends BeanPath<ManufacturedMaterialHistId> {

    private static final long serialVersionUID = 1866753598L;

    public static final QManufacturedMaterialHistId manufacturedMaterialHistId = new QManufacturedMaterialHistId("manufacturedMaterialHistId");

    public final NumberPath<Short> manufacturedMaterialSeq = createNumber("manufacturedMaterialSeq", Short.class);

    public final NumberPath<Long> materialUid = createNumber("materialUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QManufacturedMaterialHistId(String variable) {
        super(ManufacturedMaterialHistId.class, forVariable(variable));
    }

    public QManufacturedMaterialHistId(Path<? extends ManufacturedMaterialHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturedMaterialHistId(PathMetadata metadata) {
        super(ManufacturedMaterialHistId.class, metadata);
    }

}

