package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipationId is a Querydsl query type for ParticipationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QParticipationId extends BeanPath<ParticipationId> {

    private static final long serialVersionUID = 1908764389L;

    public static final QParticipationId participationId = new QParticipationId("participationId");

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final NumberPath<Long> subjectEntityUid = createNumber("subjectEntityUid", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public QParticipationId(String variable) {
        super(ParticipationId.class, forVariable(variable));
    }

    public QParticipationId(Path<? extends ParticipationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipationId(PathMetadata metadata) {
        super(ParticipationId.class, metadata);
    }

}

