package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJurisdictionParticipationId is a Querydsl query type for JurisdictionParticipationId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QJurisdictionParticipationId extends BeanPath<JurisdictionParticipationId> {

    private static final long serialVersionUID = 657054285L;

    public static final QJurisdictionParticipationId jurisdictionParticipationId = new QJurisdictionParticipationId("jurisdictionParticipationId");

    public final StringPath fipsCd = createString("fipsCd");

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath typeCd = createString("typeCd");

    public QJurisdictionParticipationId(String variable) {
        super(JurisdictionParticipationId.class, forVariable(variable));
    }

    public QJurisdictionParticipationId(Path<? extends JurisdictionParticipationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJurisdictionParticipationId(PathMetadata metadata) {
        super(JurisdictionParticipationId.class, metadata);
    }

}

