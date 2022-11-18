package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsPageHist is a Querydsl query type for NbsPageHist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsPageHist extends EntityPathBase<NbsPageHist> {

    private static final long serialVersionUID = -449309223L;

    public static final QNbsPageHist nbsPageHist = new QNbsPageHist("nbsPageHist");

    public final StringPath busObjType = createString("busObjType");

    public final StringPath datamartNm = createString("datamartNm");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath formCd = createString("formCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ArrayPath<byte[], Byte> jspPayload = createArray("jspPayload", byte[].class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final NumberPath<Long> nbsPageUid = createNumber("nbsPageUid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Integer> versionCtrlNbr = createNumber("versionCtrlNbr", Integer.class);

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public QNbsPageHist(String variable) {
        super(NbsPageHist.class, forVariable(variable));
    }

    public QNbsPageHist(Path<? extends NbsPageHist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsPageHist(PathMetadata metadata) {
        super(NbsPageHist.class, metadata);
    }

}

