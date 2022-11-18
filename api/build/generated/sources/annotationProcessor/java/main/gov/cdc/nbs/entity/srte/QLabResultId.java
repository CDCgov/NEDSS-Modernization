package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabResultId is a Querydsl query type for LabResultId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLabResultId extends BeanPath<LabResultId> {

    private static final long serialVersionUID = -108636009L;

    public static final QLabResultId labResultId = new QLabResultId("labResultId");

    public final StringPath laboratoryId = createString("laboratoryId");

    public final StringPath labResultCd = createString("labResultCd");

    public QLabResultId(String variable) {
        super(LabResultId.class, forVariable(variable));
    }

    public QLabResultId(Path<? extends LabResultId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabResultId(PathMetadata metadata) {
        super(LabResultId.class, metadata);
    }

}

