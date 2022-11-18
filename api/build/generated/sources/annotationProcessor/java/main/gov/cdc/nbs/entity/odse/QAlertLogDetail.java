package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertLogDetail is a Querydsl query type for AlertLogDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertLogDetail extends EntityPathBase<AlertLogDetail> {

    private static final long serialVersionUID = 1648076016L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertLogDetail alertLogDetail = new QAlertLogDetail("alertLogDetail");

    public final StringPath alertActivityDetailLog = createString("alertActivityDetailLog");

    public final QAlertLog alertLogUid;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAlertLogDetail(String variable) {
        this(AlertLogDetail.class, forVariable(variable), INITS);
    }

    public QAlertLogDetail(Path<? extends AlertLogDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertLogDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertLogDetail(PathMetadata metadata, PathInits inits) {
        this(AlertLogDetail.class, metadata, inits);
    }

    public QAlertLogDetail(Class<? extends AlertLogDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertLogUid = inits.isInitialized("alertLogUid") ? new QAlertLog(forProperty("alertLogUid"), inits.get("alertLogUid")) : null;
    }

}

