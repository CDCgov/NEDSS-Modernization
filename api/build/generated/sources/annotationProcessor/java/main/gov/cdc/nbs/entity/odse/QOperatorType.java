package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOperatorType is a Querydsl query type for OperatorType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOperatorType extends EntityPathBase<OperatorType> {

    private static final long serialVersionUID = 102268405L;

    public static final QOperatorType operatorType = new QOperatorType("operatorType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath operatorTypeCode = createString("operatorTypeCode");

    public final StringPath operatorTypeDescTxt = createString("operatorTypeDescTxt");

    public QOperatorType(String variable) {
        super(OperatorType.class, forVariable(variable));
    }

    public QOperatorType(Path<? extends OperatorType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperatorType(PathMetadata metadata) {
        super(OperatorType.class, metadata);
    }

}

