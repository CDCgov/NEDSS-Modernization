package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueCoded is a Querydsl query type for ObsValueCoded
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueCoded extends EntityPathBase<ObsValueCoded> {

    private static final long serialVersionUID = -1527623377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueCoded obsValueCoded = new QObsValueCoded("obsValueCoded");

    public final StringPath altCd = createString("altCd");

    public final StringPath altCdDescTxt = createString("altCdDescTxt");

    public final StringPath altCdSystemCd = createString("altCdSystemCd");

    public final StringPath altCdSystemDescTxt = createString("altCdSystemDescTxt");

    public final ComparablePath<Character> codeDerivedInd = createComparable("codeDerivedInd", Character.class);

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final StringPath displayName = createString("displayName");

    public final QObsValueCodedId id;

    public final QObservation observationUid;

    public final StringPath originalTxt = createString("originalTxt");

    public QObsValueCoded(String variable) {
        this(ObsValueCoded.class, forVariable(variable), INITS);
    }

    public QObsValueCoded(Path<? extends ObsValueCoded> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueCoded(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueCoded(PathMetadata metadata, PathInits inits) {
        this(ObsValueCoded.class, metadata, inits);
    }

    public QObsValueCoded(Class<? extends ObsValueCoded> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueCodedId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

