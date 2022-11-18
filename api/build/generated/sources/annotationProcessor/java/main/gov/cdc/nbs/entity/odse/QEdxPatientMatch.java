package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEdxPatientMatch is a Querydsl query type for EdxPatientMatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxPatientMatch extends EntityPathBase<EdxPatientMatch> {

    private static final long serialVersionUID = -2043261758L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEdxPatientMatch edxPatientMatch = new QEdxPatientMatch("edxPatientMatch");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath matchString = createString("matchString");

    public final NumberPath<Long> matchStringHashcode = createNumber("matchStringHashcode", Long.class);

    public final QNBSEntity patientUid;

    public final StringPath typeCd = createString("typeCd");

    public QEdxPatientMatch(String variable) {
        this(EdxPatientMatch.class, forVariable(variable), INITS);
    }

    public QEdxPatientMatch(Path<? extends EdxPatientMatch> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEdxPatientMatch(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEdxPatientMatch(PathMetadata metadata, PathInits inits) {
        this(EdxPatientMatch.class, metadata, inits);
    }

    public QEdxPatientMatch(Class<? extends EdxPatientMatch> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.patientUid = inits.isInitialized("patientUid") ? new QNBSEntity(forProperty("patientUid")) : null;
    }

}

