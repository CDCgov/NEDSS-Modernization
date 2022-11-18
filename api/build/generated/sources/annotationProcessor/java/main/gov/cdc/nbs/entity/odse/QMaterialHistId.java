package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMaterialHistId is a Querydsl query type for MaterialHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMaterialHistId extends BeanPath<MaterialHistId> {

    private static final long serialVersionUID = -516726245L;

    public static final QMaterialHistId materialHistId = new QMaterialHistId("materialHistId");

    public final NumberPath<Long> materialUid = createNumber("materialUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QMaterialHistId(String variable) {
        super(MaterialHistId.class, forVariable(variable));
    }

    public QMaterialHistId(Path<? extends MaterialHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMaterialHistId(PathMetadata metadata) {
        super(MaterialHistId.class, metadata);
    }

}

