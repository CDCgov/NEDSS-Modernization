package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlertEmailMessage is a Querydsl query type for AlertEmailMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlertEmailMessage extends EntityPathBase<AlertEmailMessage> {

    private static final long serialVersionUID = -1629514064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlertEmailMessage alertEmailMessage = new QAlertEmailMessage("alertEmailMessage");

    public final DateTimePath<java.time.Instant> alertAddTime = createDateTime("alertAddTime", java.time.Instant.class);

    public final QAlert alertUid;

    public final StringPath associatedConditionCd = createString("associatedConditionCd");

    public final StringPath associatedConditionDesc = createString("associatedConditionDesc");

    public final DateTimePath<java.time.Instant> emailSentTime = createDateTime("emailSentTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> eventAddTime = createDateTime("eventAddTime", java.time.Instant.class);

    public final StringPath eventLocalId = createString("eventLocalId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final StringPath jurisdictionDescription = createString("jurisdictionDescription");

    public final StringPath progAreaCd = createString("progAreaCd");

    public final StringPath progAreaDescription = createString("progAreaDescription");

    public final StringPath severity = createString("severity");

    public final StringPath severityCd = createString("severityCd");

    public final ComparablePath<Character> simulatedAlert = createComparable("simulatedAlert", Character.class);

    public final StringPath transmissionStatus = createString("transmissionStatus");

    public final StringPath type = createString("type");

    public final StringPath typeCd = createString("typeCd");

    public QAlertEmailMessage(String variable) {
        this(AlertEmailMessage.class, forVariable(variable), INITS);
    }

    public QAlertEmailMessage(Path<? extends AlertEmailMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlertEmailMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlertEmailMessage(PathMetadata metadata, PathInits inits) {
        this(AlertEmailMessage.class, metadata, inits);
    }

    public QAlertEmailMessage(Class<? extends AlertEmailMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.alertUid = inits.isInitialized("alertUid") ? new QAlert(forProperty("alertUid")) : null;
    }

}

