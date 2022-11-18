package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWaRdbMetadatum is a Querydsl query type for WaRdbMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWaRdbMetadatum extends EntityPathBase<WaRdbMetadatum> {

    private static final long serialVersionUID = 241072411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWaRdbMetadatum waRdbMetadatum = new QWaRdbMetadatum("waRdbMetadatum");

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

    public final QWaTemplate waTemplateUid;

    public final NumberPath<Long> waUiMetadataUid = createNumber("waUiMetadataUid", Long.class);

    public QWaRdbMetadatum(String variable) {
        this(WaRdbMetadatum.class, forVariable(variable), INITS);
    }

    public QWaRdbMetadatum(Path<? extends WaRdbMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWaRdbMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWaRdbMetadatum(PathMetadata metadata, PathInits inits) {
        this(WaRdbMetadatum.class, metadata, inits);
    }

    public QWaRdbMetadatum(Class<? extends WaRdbMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.waTemplateUid = inits.isInitialized("waTemplateUid") ? new QWaTemplate(forProperty("waTemplateUid")) : null;
    }

}

