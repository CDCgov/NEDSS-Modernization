package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabTestId is a Querydsl query type for LabTestId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLabTestId extends BeanPath<LabTestId> {

    private static final long serialVersionUID = 1706303372L;

    public static final QLabTestId labTestId = new QLabTestId("labTestId");

    public final StringPath laboratoryId = createString("laboratoryId");

    public final StringPath labTestCd = createString("labTestCd");

    public QLabTestId(String variable) {
        super(LabTestId.class, forVariable(variable));
    }

    public QLabTestId(Path<? extends LabTestId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabTestId(PathMetadata metadata) {
        super(LabTestId.class, metadata);
    }

}

