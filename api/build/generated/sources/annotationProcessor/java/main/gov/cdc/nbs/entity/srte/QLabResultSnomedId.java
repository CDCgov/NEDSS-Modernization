package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLabResultSnomedId is a Querydsl query type for LabResultSnomedId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLabResultSnomedId extends BeanPath<LabResultSnomedId> {

    private static final long serialVersionUID = 2023011727L;

    public static final QLabResultSnomedId labResultSnomedId = new QLabResultSnomedId("labResultSnomedId");

    public final StringPath laboratoryId = createString("laboratoryId");

    public final StringPath labResultCd = createString("labResultCd");

    public final StringPath snomedCd = createString("snomedCd");

    public QLabResultSnomedId(String variable) {
        super(LabResultSnomedId.class, forVariable(variable));
    }

    public QLabResultSnomedId(Path<? extends LabResultSnomedId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabResultSnomedId(PathMetadata metadata) {
        super(LabResultSnomedId.class, metadata);
    }

}

