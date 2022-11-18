package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJurisdictionParticipation is a Querydsl query type for JurisdictionParticipation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJurisdictionParticipation extends EntityPathBase<JurisdictionParticipation> {

    private static final long serialVersionUID = 925822354L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJurisdictionParticipation jurisdictionParticipation = new QJurisdictionParticipation("jurisdictionParticipation");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QJurisdictionParticipationId id;

    public final QJurisdictionCode jurisdictionCd;

    public QJurisdictionParticipation(String variable) {
        this(JurisdictionParticipation.class, forVariable(variable), INITS);
    }

    public QJurisdictionParticipation(Path<? extends JurisdictionParticipation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJurisdictionParticipation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJurisdictionParticipation(PathMetadata metadata, PathInits inits) {
        this(JurisdictionParticipation.class, metadata, inits);
    }

    public QJurisdictionParticipation(Class<? extends JurisdictionParticipation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QJurisdictionParticipationId(forProperty("id")) : null;
        this.jurisdictionCd = inits.isInitialized("jurisdictionCd") ? new QJurisdictionCode(forProperty("jurisdictionCd")) : null;
    }

}

