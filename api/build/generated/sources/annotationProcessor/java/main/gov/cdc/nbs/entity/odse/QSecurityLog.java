package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSecurityLog is a Querydsl query type for SecurityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSecurityLog extends EntityPathBase<SecurityLog> {

    private static final long serialVersionUID = -244495091L;

    public static final QSecurityLog securityLog = new QSecurityLog("securityLog");

    public final DateTimePath<java.time.Instant> eventTime = createDateTime("eventTime", java.time.Instant.class);

    public final StringPath eventTypeCd = createString("eventTypeCd");

    public final StringPath firstNm = createString("firstNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastNm = createString("lastNm");

    public final NumberPath<Long> nedssEntryId = createNumber("nedssEntryId", Long.class);

    public final StringPath sessionId = createString("sessionId");

    public final StringPath userId = createString("userId");

    public final StringPath userIpAddr = createString("userIpAddr");

    public QSecurityLog(String variable) {
        super(SecurityLog.class, forVariable(variable));
    }

    public QSecurityLog(Path<? extends SecurityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSecurityLog(PathMetadata metadata) {
        super(SecurityLog.class, metadata);
    }

}

