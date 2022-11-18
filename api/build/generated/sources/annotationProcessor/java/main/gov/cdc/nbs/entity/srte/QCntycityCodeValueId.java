package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCntycityCodeValueId is a Querydsl query type for CntycityCodeValueId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCntycityCodeValueId extends BeanPath<CntycityCodeValueId> {

    private static final long serialVersionUID = -155135242L;

    public static final QCntycityCodeValueId cntycityCodeValueId = new QCntycityCodeValueId("cntycityCodeValueId");

    public final StringPath cityCode = createString("cityCode");

    public final StringPath cntyCode = createString("cntyCode");

    public QCntycityCodeValueId(String variable) {
        super(CntycityCodeValueId.class, forVariable(variable));
    }

    public QCntycityCodeValueId(Path<? extends CntycityCodeValueId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCntycityCodeValueId(PathMetadata metadata) {
        super(CntycityCodeValueId.class, metadata);
    }

}

