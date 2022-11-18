package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipationHistId is a Querydsl query type for ParticipationHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QParticipationHistId extends BeanPath<ParticipationHistId> {

    private static final long serialVersionUID = 549524647L;

    public static final QParticipationHistId participationHistId = new QParticipationHistId("participationHistId");

    public final NumberPath<Long> actUid = createNumber("actUid", Long.class);

    public final NumberPath<Long> subjectEntityUid = createNumber("subjectEntityUid", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QParticipationHistId(String variable) {
        super(ParticipationHistId.class, forVariable(variable));
    }

    public QParticipationHistId(Path<? extends ParticipationHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipationHistId(PathMetadata metadata) {
        super(ParticipationHistId.class, metadata);
    }

}

