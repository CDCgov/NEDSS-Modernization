package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaRdbMetadataHist is a Querydsl query type for WaRdbMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaRdbMetadataHist extends EntityPathBase<WaRdbMetadataHist> {

    private static final long serialVersionUID = 583409088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaRdbMetadataHist waRdbMetadataHist = new QWaRdbMetadataHist("waRdbMetadataHist");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Integer> blockPivotNbr = createNumber("blockPivotNbr", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath rdbColumnNm = createString("rdbColumnNm");

    public final StringPath rdbTableNm = createString("rdbTableNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath rptAdminColumnNm = createString("rptAdminColumnNm");

    public final StringPath userDefinedColumnNm = createString("userDefinedColumnNm");

    public final NumberPath<Long> waRdbMetadataUid = createNumber("waRdbMetadataUid", Long.class);

    public final QWaTemplateHist waTemplateHistUid;

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public final NumberPath<Long> waUiMetadataUid = createNumber("waUiMetadataUid", Long.class);

    public QWaRdbMetadataHist(String variable) {
        this(WaRdbMetadataHist.class, forVariable(variable), INITS);
    }

    public QWaRdbMetadataHist(Path<? extends WaRdbMetadataHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaRdbMetadataHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaRdbMetadataHist(PathMetadata metadata, PathInits inits) {
        this(WaRdbMetadataHist.class, metadata, inits);
    }

    public QWaRdbMetadataHist(Class<? extends WaRdbMetadataHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateHistUid = inits.isInitialized("waTemplateHistUid") ? new QWaTemplateHist(forProperty("waTemplateHistUid")) : null;
    }

}

