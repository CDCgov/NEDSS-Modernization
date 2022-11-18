package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabtestLoincId is a Querydsl query type for LabtestLoincId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLabtestLoincId extends BeanPath<LabtestLoincId> {

    private static final long serialVersionUID = 871838277L;

    public static final QLabtestLoincId labtestLoincId = new QLabtestLoincId("labtestLoincId");

    public final StringPath laboratoryId = createString("laboratoryId");

    public final StringPath labTestCd = createString("labTestCd");

    public final StringPath loincCd = createString("loincCd");

    public QLabtestLoincId(String variable) {
        super(LabtestLoincId.class, forVariable(variable));
    }

    public QLabtestLoincId(Path<? extends LabtestLoincId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabtestLoincId(PathMetadata metadata) {
        super(LabtestLoincId.class, metadata);
    }

}

