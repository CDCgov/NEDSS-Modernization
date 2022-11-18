package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsRdbMetadatum is a Querydsl query type for NbsRdbMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsRdbMetadatum extends EntityPathBase<NbsRdbMetadatum> {

    private static final long serialVersionUID = -901050750L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsRdbMetadatum nbsRdbMetadatum = new QNbsRdbMetadatum("nbsRdbMetadatum");

    public final NumberPath<Integer> blockPivotNbr = createNumber("blockPivotNbr", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNbsPage nbsPageUid;

    public final QNbsUiMetadatum nbsUiMetadataUid;

    public final StringPath rdbColumnNm = createString("rdbColumnNm");

    public final StringPath rdbTableNm = createString("rdbTableNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath rptAdminColumnNm = createString("rptAdminColumnNm");

    public final StringPath userDefinedColumnNm = createString("userDefinedColumnNm");

    public QNbsRdbMetadatum(String variable) {
        this(NbsRdbMetadatum.class, forVariable(variable), INITS);
    }

    public QNbsRdbMetadatum(Path<? extends NbsRdbMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsRdbMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsRdbMetadatum(PathMetadata metadata, PathInits inits) {
        this(NbsRdbMetadatum.class, metadata, inits);
    }

    public QNbsRdbMetadatum(Class<? extends NbsRdbMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsPageUid = inits.isInitialized("nbsPageUid") ? new QNbsPage(forProperty("nbsPageUid")) : null;
        this.nbsUiMetadataUid = inits.isInitialized("nbsUiMetadataUid") ? new QNbsUiMetadatum(forProperty("nbsUiMetadataUid"), inits.get("nbsUiMetadataUid")) : null;
    }

}

