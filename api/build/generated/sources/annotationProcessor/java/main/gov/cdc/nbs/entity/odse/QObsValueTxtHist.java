package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueTxtHist is a Querydsl query type for ObsValueTxtHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueTxtHist extends EntityPathBase<ObsValueTxtHist> {

    private static final long serialVersionUID = -987973430L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueTxtHist obsValueTxtHist = new QObsValueTxtHist("obsValueTxtHist");

    public final StringPath dataSubtypeCd = createString("dataSubtypeCd");

    public final StringPath encodingTypeCd = createString("encodingTypeCd");

    public final QObsValueTxtHistId id;

    public final QObsValueTxt obsValueTxt;

    public final StringPath txtTypeCd = createString("txtTypeCd");

    public final ArrayPath<byte[], Byte> valueImageTxt = createArray("valueImageTxt", byte[].class);

    public final StringPath valueTxt = createString("valueTxt");

    public QObsValueTxtHist(String variable) {
        this(ObsValueTxtHist.class, forVariable(variable), INITS);
    }

    public QObsValueTxtHist(Path<? extends ObsValueTxtHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueTxtHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueTxtHist(PathMetadata metadata, PathInits inits) {
        this(ObsValueTxtHist.class, metadata, inits);
    }

    public QObsValueTxtHist(Class<? extends ObsValueTxtHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueTxtHistId(forProperty("id")) : null;
        this.obsValueTxt = inits.isInitialized("obsValueTxt") ? new QObsValueTxt(forProperty("obsValueTxt"), inits.get("obsValueTxt")) : null;
    }

}

