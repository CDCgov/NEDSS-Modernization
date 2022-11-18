package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNBSEntity is a Querydsl query type for NBSEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNBSEntity extends EntityPathBase<NBSEntity> {

    private static final long serialVersionUID = 755917163L;

    public static final QNBSEntity nBSEntity = new QNBSEntity("nBSEntity");

    public final StringPath classCd = createString("classCd");

    public final ListPath<EntityLocatorParticipation, QEntityLocatorParticipation> entityLocatorParticipations = this.<EntityLocatorParticipation, QEntityLocatorParticipation>createList("entityLocatorParticipations", EntityLocatorParticipation.class, QEntityLocatorParticipation.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Participation, QParticipation> participations = this.<Participation, QParticipation>createList("participations", Participation.class, QParticipation.class, PathInits.DIRECT2);

    public QNBSEntity(String variable) {
        super(NBSEntity.class, forVariable(variable));
    }

    public QNBSEntity(Path<? extends NBSEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNBSEntity(PathMetadata metadata) {
        super(NBSEntity.class, metadata);
    }

}

