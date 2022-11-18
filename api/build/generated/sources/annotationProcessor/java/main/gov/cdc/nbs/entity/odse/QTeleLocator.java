package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeleLocator is a Querydsl query type for TeleLocator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeleLocator extends EntityPathBase<TeleLocator> {

    private static final long serialVersionUID = 1841135285L;

    public static final QTeleLocator teleLocator = new QTeleLocator("teleLocator");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath cntryCd = createString("cntryCd");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath extensionTxt = createString("extensionTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath phoneNbrTxt = createString("phoneNbrTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath urlAddress = createString("urlAddress");

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QTeleLocator(String variable) {
        super(TeleLocator.class, forVariable(variable));
    }

    public QTeleLocator(Path<? extends TeleLocator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeleLocator(PathMetadata metadata) {
        super(TeleLocator.class, metadata);
    }

}

