package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabtestProgareaMappingId is a Querydsl query type for LabtestProgareaMappingId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLabtestProgareaMappingId extends BeanPath<LabtestProgareaMappingId> {

    private static final long serialVersionUID = -283692815L;

    public static final QLabtestProgareaMappingId labtestProgareaMappingId = new QLabtestProgareaMappingId("labtestProgareaMappingId");

    public final StringPath laboratoryId = createString("laboratoryId");

    public final StringPath labTestCd = createString("labTestCd");

    public QLabtestProgareaMappingId(String variable) {
        super(LabtestProgareaMappingId.class, forVariable(variable));
    }

    public QLabtestProgareaMappingId(Path<? extends LabtestProgareaMappingId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabtestProgareaMappingId(PathMetadata metadata) {
        super(LabtestProgareaMappingId.class, metadata);
    }

}

