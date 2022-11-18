package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsDocument is a Querydsl query type for NbsDocument
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsDocument extends EntityPathBase<NbsDocument> {

    private static final long serialVersionUID = -446689117L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsDocument nbsDocument = new QNbsDocument("nbsDocument");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cd = createString("cd");

    public final StringPath cdDescTxt = createString("cdDescTxt");

    public final StringPath docPayload = createString("docPayload");

    public final StringPath docPurposeCd = createString("docPurposeCd");

    public final StringPath docStatusCd = createString("docStatusCd");

    public final StringPath docTypeCd = createString("docTypeCd");

    public final NumberPath<Short> externalVersionCtrlNbr = createNumber("externalVersionCtrlNbr", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final QNbsDocumentMetadatum nbsDocumentMetadataUid;

    public final NumberPath<Long> nbsInterfaceUid = createNumber("nbsInterfaceUid", Long.class);

    public final StringPath payloadViewIndCd = createString("payloadViewIndCd");

    public final StringPath phdcDocDerived = createString("phdcDocDerived");

    public final StringPath processingDecisionCd = createString("processingDecisionCd");

    public final StringPath processingDecisionTxt = createString("processingDecisionTxt");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final NumberPath<Long> programJurisdictionOid = createNumber("programJurisdictionOid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath sendingAppEventId = createString("sendingAppEventId");

    public final StringPath sendingAppPatientId = createString("sendingAppPatientId");

    public final StringPath sendingFacilityNm = createString("sendingFacilityNm");

    public final ComparablePath<Character> sharedInd = createComparable("sharedInd", Character.class);

    public final StringPath txt = createString("txt");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QNbsDocument(String variable) {
        this(NbsDocument.class, forVariable(variable), INITS);
    }

    public QNbsDocument(Path<? extends NbsDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsDocument(PathMetadata metadata, PathInits inits) {
        this(NbsDocument.class, metadata, inits);
    }

    public QNbsDocument(Class<? extends NbsDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsDocumentMetadataUid = inits.isInitialized("nbsDocumentMetadataUid") ? new QNbsDocumentMetadatum(forProperty("nbsDocumentMetadataUid")) : null;
    }

}

