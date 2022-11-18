package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QObsValueCodedModId is a Querydsl query type for ObsValueCodedModId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QObsValueCodedModId extends BeanPath<ObsValueCodedModId> {

    private static final long serialVersionUID = 796728270L;

    public static final QObsValueCodedModId obsValueCodedModId = new QObsValueCodedModId("obsValueCodedModId");

    public final StringPath code = createString("code");

    public final StringPath codeModCd = createString("codeModCd");

    public final NumberPath<Long> observationUid = createNumber("observationUid", Long.class);

    public QObsValueCodedModId(String variable) {
        super(ObsValueCodedModId.class, forVariable(variable));
    }

    public QObsValueCodedModId(Path<? extends ObsValueCodedModId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObsValueCodedModId(PathMetadata metadata) {
        super(ObsValueCodedModId.class, metadata);
    }

}

