package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsConversionError is a Querydsl query type for NbsConversionError
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConversionError extends EntityPathBase<NbsConversionError> {

    private static final long serialVersionUID = 1376653834L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsConversionError nbsConversionError = new QNbsConversionError("nbsConversionError");

    public final NumberPath<Long> conditionCdGroupId = createNumber("conditionCdGroupId", Long.class);

    public final StringPath errorCd = createString("errorCd");

    public final StringPath errorMessageTxt = createString("errorMessageTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> nbsConversionMappingUid = createNumber("nbsConversionMappingUid", Long.class);

    public final QNbsConversionMaster nbsConversionMasterUid;

    public QNbsConversionError(String variable) {
        this(NbsConversionError.class, forVariable(variable), INITS);
    }

    public QNbsConversionError(Path<? extends NbsConversionError> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsConversionError(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsConversionError(PathMetadata metadata, PathInits inits) {
        this(NbsConversionError.class, metadata, inits);
    }

    public QNbsConversionError(Class<? extends NbsConversionError> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsConversionMasterUid = inits.isInitialized("nbsConversionMasterUid") ? new QNbsConversionMaster(forProperty("nbsConversionMasterUid"), inits.get("nbsConversionMasterUid")) : null;
    }

}

