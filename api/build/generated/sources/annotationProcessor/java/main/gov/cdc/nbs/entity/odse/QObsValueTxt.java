package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QObsValueTxt is a Querydsl query type for ObsValueTxt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QObsValueTxt extends EntityPathBase<ObsValueTxt> {

    private static final long serialVersionUID = -220567160L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObsValueTxt obsValueTxt = new QObsValueTxt("obsValueTxt");

    public final StringPath dataSubtypeCd = createString("dataSubtypeCd");

    public final StringPath encodingTypeCd = createString("encodingTypeCd");

    public final QObsValueTxtId id;

    public final QObservation observationUid;

    public final StringPath txtTypeCd = createString("txtTypeCd");

    public final ArrayPath<byte[], Byte> valueImageTxt = createArray("valueImageTxt", byte[].class);

    public final StringPath valueTxt = createString("valueTxt");

    public QObsValueTxt(String variable) {
        this(ObsValueTxt.class, forVariable(variable), INITS);
    }

    public QObsValueTxt(Path<? extends ObsValueTxt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObsValueTxt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObsValueTxt(PathMetadata metadata, PathInits inits) {
        this(ObsValueTxt.class, metadata, inits);
    }

    public QObsValueTxt(Class<? extends ObsValueTxt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QObsValueTxtId(forProperty("id")) : null;
        this.observationUid = inits.isInitialized("observationUid") ? new QObservation(forProperty("observationUid"), inits.get("observationUid")) : null;
    }

}

