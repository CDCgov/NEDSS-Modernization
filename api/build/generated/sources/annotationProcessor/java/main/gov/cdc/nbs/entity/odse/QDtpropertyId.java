package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDtpropertyId is a Querydsl query type for DtpropertyId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDtpropertyId extends BeanPath<DtpropertyId> {

    private static final long serialVersionUID = 1306960471L;

    public static final QDtpropertyId dtpropertyId = new QDtpropertyId("dtpropertyId");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath property = createString("property");

    public QDtpropertyId(String variable) {
        super(DtpropertyId.class, forVariable(variable));
    }

    public QDtpropertyId(Path<? extends DtpropertyId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDtpropertyId(PathMetadata metadata) {
        super(DtpropertyId.class, metadata);
    }

}

