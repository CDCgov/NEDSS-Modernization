package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlert is a Querydsl query type for Alert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlert extends EntityPathBase<Alert> {

    private static final long serialVersionUID = 1182317893L;

    public static final QAlert alert = new QAlert("alert");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath alertMsgTxt = createString("alertMsgTxt");

    public final StringPath conditionCd = createString("conditionCd");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurisdictionCd = createString("jurisdictionCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final StringPath severityCd = createString("severityCd");

    public final StringPath typeCd = createString("typeCd");

    public QAlert(String variable) {
        super(Alert.class, forVariable(variable));
    }

    public QAlert(Path<? extends Alert> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlert(PathMetadata metadata) {
        super(Alert.class, metadata);
    }

}

