package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsPage is a Querydsl query type for NbsPage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsPage extends EntityPathBase<NbsPage> {

    private static final long serialVersionUID = 714384919L;

    public static final QNbsPage nbsPage = new QNbsPage("nbsPage");

    public final StringPath busObjType = createString("busObjType");

    public final StringPath datamartNm = createString("datamartNm");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath formCd = createString("formCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ArrayPath<byte[], Byte> jspPayload = createArray("jspPayload", byte[].class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath localId = createString("localId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final NumberPath<Long> waTemplateUid = createNumber("waTemplateUid", Long.class);

    public QNbsPage(String variable) {
        super(NbsPage.class, forVariable(variable));
    }

    public QNbsPage(Path<? extends NbsPage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsPage(PathMetadata metadata) {
        super(NbsPage.class, metadata);
    }

}

