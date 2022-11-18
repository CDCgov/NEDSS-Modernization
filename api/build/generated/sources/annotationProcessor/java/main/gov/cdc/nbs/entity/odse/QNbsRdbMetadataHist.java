package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsRdbMetadataHist is a Querydsl query type for NbsRdbMetadataHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsRdbMetadataHist extends EntityPathBase<NbsRdbMetadataHist> {

    private static final long serialVersionUID = 323238649L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsRdbMetadataHist nbsRdbMetadataHist = new QNbsRdbMetadataHist("nbsRdbMetadataHist");

    public final NumberPath<Integer> blockPivotNbr = createNumber("blockPivotNbr", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNbsPage nbsPageUid;

    public final NumberPath<Long> nbsRdbMetadataUid = createNumber("nbsRdbMetadataUid", Long.class);

    public final QNbsUiMetadatum nbsUiMetadataUid;

    public final StringPath rdbColumnNm = createString("rdbColumnNm");

    public final StringPath rdbTableNm = createString("rdbTableNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath rptAdminColumnNm = createString("rptAdminColumnNm");

    public final StringPath userDefinedColumnNm = createString("userDefinedColumnNm");

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public QNbsRdbMetadataHist(String variable) {
        this(NbsRdbMetadataHist.class, forVariable(variable), INITS);
    }

    public QNbsRdbMetadataHist(Path<? extends NbsRdbMetadataHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsRdbMetadataHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsRdbMetadataHist(PathMetadata metadata, PathInits inits) {
        this(NbsRdbMetadataHist.class, metadata, inits);
    }

    public QNbsRdbMetadataHist(Class<? extends NbsRdbMetadataHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsPageUid = inits.isInitialized("nbsPageUid") ? new QNbsPage(forProperty("nbsPageUid")) : null;
        this.nbsUiMetadataUid = inits.isInitialized("nbsUiMetadataUid") ? new QNbsUiMetadatum(forProperty("nbsUiMetadataUid"), inits.get("nbsUiMetadataUid")) : null;
    }

}

