package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsConversionPageMgmt is a Querydsl query type for NbsConversionPageMgmt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConversionPageMgmt extends EntityPathBase<NbsConversionPageMgmt> {

    private static final long serialVersionUID = -1285112658L;

    public static final QNbsConversionPageMgmt nbsConversionPageMgmt = new QNbsConversionPageMgmt("nbsConversionPageMgmt");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath fromPageFormCd = createString("fromPageFormCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath mapName = createString("mapName");

    public final StringPath mappingStatusCd = createString("mappingStatusCd");

    public final StringPath toPageFormCd = createString("toPageFormCd");

    public final StringPath xmlPayload = createString("xmlPayload");

    public QNbsConversionPageMgmt(String variable) {
        super(NbsConversionPageMgmt.class, forVariable(variable));
    }

    public QNbsConversionPageMgmt(Path<? extends NbsConversionPageMgmt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsConversionPageMgmt(PathMetadata metadata) {
        super(NbsConversionPageMgmt.class, metadata);
    }

}

