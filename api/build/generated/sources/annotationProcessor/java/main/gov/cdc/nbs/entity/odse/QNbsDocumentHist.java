package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsDocumentHist is a Querydsl query type for NbsDocumentHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsDocumentHist extends EntityPathBase<NbsDocumentHist> {

    private static final long serialVersionUID = 536042085L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsDocumentHist nbsDocumentHist = new QNbsDocumentHist("nbsDocumentHist");

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

    public final NumberPath<Long> nbsDocumentMetadataUid = createNumber("nbsDocumentMetadataUid", Long.class);

    public final QNbsDocument nbsDocumentUid;

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

    public QNbsDocumentHist(String variable) {
        this(NbsDocumentHist.class, forVariable(variable), INITS);
    }

    public QNbsDocumentHist(Path<? extends NbsDocumentHist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsDocumentHist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsDocumentHist(PathMetadata metadata, PathInits inits) {
        this(NbsDocumentHist.class, metadata, inits);
    }

    public QNbsDocumentHist(Class<? extends NbsDocumentHist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsDocumentUid = inits.isInitialized("nbsDocumentUid") ? new QNbsDocument(forProperty("nbsDocumentUid"), inits.get("nbsDocumentUid")) : null;
    }

}

