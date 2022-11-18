package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueCodedModHist is a Querydsl query type for ObsValueCodedModHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueCodedModHist extends EntityPathBase<ObsValueCodedModHist> {

    private static final long serialVersionUID = 1151667477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueCodedModHist obsValueCodedModHist = new QObsValueCodedModHist("obsValueCodedModHist");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final StringPath displayName = createString("displayName");

    public final QObsValueCodedModHistId id;

    public final StringPath originalTxt = createString("originalTxt");

    public QObsValueCodedModHist(String variable) {
        this(ObsValueCodedModHist.class, forVariable(variable), INITS);
    }

    public QObsValueCodedModHist(Path<? extends ObsValueCodedModHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueCodedModHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueCodedModHist(PathMetadata metadata, PathInits inits) {
        this(ObsValueCodedModHist.class, metadata, inits);
    }

    public QObsValueCodedModHist(Class<? extends ObsValueCodedModHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueCodedModHistId(forProperty("id")) : null;
    }

}

