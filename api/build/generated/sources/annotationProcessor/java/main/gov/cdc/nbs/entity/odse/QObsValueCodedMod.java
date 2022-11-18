package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueCodedMod is a Querydsl query type for ObsValueCodedMod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueCodedMod extends EntityPathBase<ObsValueCodedMod> {

    private static final long serialVersionUID = 45521747L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueCodedMod obsValueCodedMod = new QObsValueCodedMod("obsValueCodedMod");

    public final StringPath codeSystemCd = createString("codeSystemCd");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codeVersion = createString("codeVersion");

    public final StringPath displayName = createString("displayName");

    public final QObsValueCodedModId id;

    public final StringPath originalTxt = createString("originalTxt");

    public QObsValueCodedMod(String variable) {
        this(ObsValueCodedMod.class, forVariable(variable), INITS);
    }

    public QObsValueCodedMod(Path<? extends ObsValueCodedMod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueCodedMod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueCodedMod(PathMetadata metadata, PathInits inits) {
        this(ObsValueCodedMod.class, metadata, inits);
    }

    public QObsValueCodedMod(Class<? extends ObsValueCodedMod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueCodedModId(forProperty("id")) : null;
    }

}

