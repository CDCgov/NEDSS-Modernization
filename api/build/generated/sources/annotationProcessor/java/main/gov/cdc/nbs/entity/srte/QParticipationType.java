package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipationType is a Querydsl query type for ParticipationType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipationType extends EntityPathBase<ParticipationType> {

    private static final long serialVersionUID = -935275411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipationType participationType = new QParticipationType("participationType");

    public final QParticipationTypeId id;

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath typeDescTxt = createString("typeDescTxt");

    public final StringPath typePrefix = createString("typePrefix");

    public QParticipationType(String variable) {
        this(ParticipationType.class, forVariable(variable), INITS);
    }

    public QParticipationType(Path<? extends ParticipationType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipationType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipationType(PathMetadata metadata, PathInits inits) {
        this(ParticipationType.class, metadata, inits);
    }

    public QParticipationType(Class<? extends ParticipationType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QParticipationTypeId(forProperty("id")) : null;
    }

}

