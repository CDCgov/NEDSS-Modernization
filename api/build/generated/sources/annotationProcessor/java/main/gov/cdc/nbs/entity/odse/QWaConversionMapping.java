package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaConversionMapping is a Querydsl query type for WaConversionMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaConversionMapping extends EntityPathBase<WaConversionMapping> {

    private static final long serialVersionUID = -948327081L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaConversionMapping waConversionMapping = new QWaConversionMapping("waConversionMapping");

    public final NumberPath<Integer> answerGroupSeqNbr = createNumber("answerGroupSeqNbr", Integer.class);

    public final StringPath answerMapped = createString("answerMapped");

    public final NumberPath<Integer> blockIdNbr = createNumber("blockIdNbr", Integer.class);

    public final StringPath conversionType = createString("conversionType");

    public final StringPath fromAnswer = createString("fromAnswer");

    public final StringPath fromQuestionId = createString("fromQuestionId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mappingStatus = createString("mappingStatus");

    public final QNbsConversionPageMgmt nbsConversionPageMgmtUid;

    public final StringPath questionMapped = createString("questionMapped");

    public final StringPath toAnswer = createString("toAnswer");

    public final NumberPath<Long> toCodeSetGroupId = createNumber("toCodeSetGroupId", Long.class);

    public final StringPath toDataType = createString("toDataType");

    public final NumberPath<Long> toNbsUiComponentUid = createNumber("toNbsUiComponentUid", Long.class);

    public final StringPath toQuestionId = createString("toQuestionId");

    public QWaConversionMapping(String variable) {
        this(WaConversionMapping.class, forVariable(variable), INITS);
    }

    public QWaConversionMapping(Path<? extends WaConversionMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaConversionMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaConversionMapping(PathMetadata metadata, PathInits inits) {
        this(WaConversionMapping.class, metadata, inits);
    }

    public QWaConversionMapping(Class<? extends WaConversionMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsConversionPageMgmtUid = inits.isInitialized("nbsConversionPageMgmtUid") ? new QNbsConversionPageMgmt(forProperty("nbsConversionPageMgmtUid")) : null;
    }

}

