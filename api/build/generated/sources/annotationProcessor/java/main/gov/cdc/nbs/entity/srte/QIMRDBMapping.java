package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIMRDBMapping is a Querydsl query type for IMRDBMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIMRDBMapping extends EntityPathBase<IMRDBMapping> {

    private static final long serialVersionUID = -300098864L;

    public static final QIMRDBMapping iMRDBMapping = new QIMRDBMapping("iMRDBMapping");

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath dbField = createString("dbField");

    public final StringPath dbTable = createString("dbTable");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath otherAttributes = createString("otherAttributes");

    public final StringPath rdbAttribute = createString("rdbAttribute");

    public final StringPath rdbTable = createString("rdbTable");

    public final StringPath uniqueCd = createString("uniqueCd");

    public final StringPath uniqueName = createString("uniqueName");

    public QIMRDBMapping(String variable) {
        super(IMRDBMapping.class, forVariable(variable));
    }

    public QIMRDBMapping(Path<? extends IMRDBMapping> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIMRDBMapping(PathMetadata metadata) {
        super(IMRDBMapping.class, metadata);
    }

}

