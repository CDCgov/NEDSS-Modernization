package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManufacturedMaterialId is a Querydsl query type for ManufacturedMaterialId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QManufacturedMaterialId extends BeanPath<ManufacturedMaterialId> {

    private static final long serialVersionUID = 1088889852L;

    public static final QManufacturedMaterialId manufacturedMaterialId = new QManufacturedMaterialId("manufacturedMaterialId");

    public final NumberPath<Short> manufacturedMaterialSeq = createNumber("manufacturedMaterialSeq", Short.class);

    public final NumberPath<Long> materialUid = createNumber("materialUid", Long.class);

    public QManufacturedMaterialId(String variable) {
        super(ManufacturedMaterialId.class, forVariable(variable));
    }

    public QManufacturedMaterialId(Path<? extends ManufacturedMaterialId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManufacturedMaterialId(PathMetadata metadata) {
        super(ManufacturedMaterialId.class, metadata);
    }

}

