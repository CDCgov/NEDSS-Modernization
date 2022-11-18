package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QZipcntyCodeValueId is a Querydsl query type for ZipcntyCodeValueId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QZipcntyCodeValueId extends BeanPath<ZipcntyCodeValueId> {

    private static final long serialVersionUID = 2146629148L;

    public static final QZipcntyCodeValueId zipcntyCodeValueId = new QZipcntyCodeValueId("zipcntyCodeValueId");

    public final StringPath cntyCode = createString("cntyCode");

    public final StringPath zipCode = createString("zipCode");

    public QZipcntyCodeValueId(String variable) {
        super(ZipcntyCodeValueId.class, forVariable(variable));
    }

    public QZipcntyCodeValueId(Path<? extends ZipcntyCodeValueId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QZipcntyCodeValueId(PathMetadata metadata) {
        super(ZipcntyCodeValueId.class, metadata);
    }

}

