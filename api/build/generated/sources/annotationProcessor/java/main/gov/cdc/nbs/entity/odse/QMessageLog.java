package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessageLog is a Querydsl query type for MessageLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageLog extends EntityPathBase<MessageLog> {

    private static final long serialVersionUID = 745222004L;

    public static final QMessageLog messageLog = new QMessageLog("messageLog");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> assignedToUid = createNumber("assignedToUid", Long.class);

    public final StringPath conditionCd = createString("conditionCd");

    public final StringPath eventTypeCd = createString("eventTypeCd");

    public final NumberPath<Long> eventUid = createNumber("eventUid", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath messageStatusCd = createString("messageStatusCd");

    public final StringPath messageTxt = createString("messageTxt");

    public final NumberPath<Long> personUid = createNumber("personUid", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QMessageLog(String variable) {
        super(MessageLog.class, forVariable(variable));
    }

    public QMessageLog(Path<? extends MessageLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageLog(PathMetadata metadata) {
        super(MessageLog.class, metadata);
    }

}

