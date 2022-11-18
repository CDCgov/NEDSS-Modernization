package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueCodedHist is a Querydsl query type for ObsValueCodedHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueCodedHist extends EntityPathBase<ObsValueCodedHist> {

    private static final long serialVersionUID = 1411020017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueCodedHist obsValueCodedHist = new QObsValueCodedHist("obsValueCodedHist");

    public final StringPath altCd = createString("altCd");

    public final StringPath altCdDescTxt = createString("altCdDescTxt");

    public final StringPath altCdSystemCd = createString("altCdSystemCd");

    public final StringPath altCdSystemDescTxt = createString("altCdSystemDescTxt");

    public final ComparablePath<Character> codeDerivedInd = createComparable("codeDerivedInd", Character.class);

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final StringPath displayName = createString("displayName");

    public final QObsValueCodedHistId id;

    public final StringPath originalTxt = createString("originalTxt");

    public QObsValueCodedHist(String variable) {
        this(ObsValueCodedHist.class, forVariable(variable), INITS);
    }

    public QObsValueCodedHist(Path<? extends ObsValueCodedHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueCodedHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueCodedHist(PathMetadata metadata, PathInits inits) {
        this(ObsValueCodedHist.class, metadata, inits);
    }

    public QObsValueCodedHist(Class<? extends ObsValueCodedHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueCodedHistId(forProperty("id")) : null;
    }

}

