package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAct is a Querydsl query type for Act
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAct extends EntityPathBase<Act> {

    private static final long serialVersionUID = -3239237L;

    public static final QAct act = new QAct("act");

    public final ListPath<ActId, QActId> actIds = this.<ActId, QActId>createList("actIds", ActId.class, QActId.class, PathInits.DIRECT2);

    public final ListPath<ActRelationship, QActRelationship> actRelationships = this.<ActRelationship, QActRelationship>createList("actRelationships", ActRelationship.class, QActRelationship.class, PathInits.DIRECT2);

    public final StringPath classCd = createString("classCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath moodCd = createString("moodCd");

    public final ListPath<Notification, QNotification> notifications = this.<Notification, QNotification>createList("notifications", Notification.class, QNotification.class, PathInits.DIRECT2);

    public final ListPath<Observation, QObservation> observations = this.<Observation, QObservation>createList("observations", Observation.class, QObservation.class, PathInits.DIRECT2);

    public final ListPath<Participation, QParticipation> participations = this.<Participation, QParticipation>createList("participations", Participation.class, QParticipation.class, PathInits.DIRECT2);

    public final ListPath<PublicHealthCase, QPublicHealthCase> publicHealthCases = this.<PublicHealthCase, QPublicHealthCase>createList("publicHealthCases", PublicHealthCase.class, QPublicHealthCase.class, PathInits.DIRECT2);

    public final ListPath<ActRelationship, QActRelationship> targetActRelationships = this.<ActRelationship, QActRelationship>createList("targetActRelationships", ActRelationship.class, QActRelationship.class, PathInits.DIRECT2);

    public QAct(String variable) {
        super(Act.class, forVariable(variable));
    }

    public QAct(Path<? extends Act> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAct(PathMetadata metadata) {
        super(Act.class, metadata);
    }

}

