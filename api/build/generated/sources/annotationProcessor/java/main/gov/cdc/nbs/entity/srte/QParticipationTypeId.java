package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QParticipationTypeId is a Querydsl query type for ParticipationTypeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QParticipationTypeId extends BeanPath<ParticipationTypeId> {

    private static final long serialVersionUID = -1151502744L;

    public static final QParticipationTypeId participationTypeId = new QParticipationTypeId("participationTypeId");

    public final StringPath actClassCd = createString("actClassCd");

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath subjectClassCd = createString("subjectClassCd");

    public final StringPath typeCd = createString("typeCd");

    public QParticipationTypeId(String variable) {
        super(ParticipationTypeId.class, forVariable(variable));
    }

    public QParticipationTypeId(Path<? extends ParticipationTypeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipationTypeId(PathMetadata metadata) {
        super(ParticipationTypeId.class, metadata);
    }

}

