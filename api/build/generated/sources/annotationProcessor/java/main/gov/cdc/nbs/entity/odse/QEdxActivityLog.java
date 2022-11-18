package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEdxActivityLog is a Querydsl query type for EdxActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEdxActivityLog extends EntityPathBase<EdxActivityLog> {

    private static final long serialVersionUID = 1677954675L;

    public static final QEdxActivityLog edxActivityLog = new QEdxActivityLog("edxActivityLog");

    public final StringPath accessionNbr = createString("accessionNbr");

    public final StringPath algorithmAction = createString("algorithmAction");

    public final StringPath algorithmName = createString("algorithmName");

    public final StringPath businessObjLocalid = createString("businessObjLocalid");

    public final StringPath docNm = createString("docNm");

    public final StringPath docType = createString("docType");

    public final StringPath entityNm = createString("entityNm");

    public final StringPath exceptionTxt = createString("exceptionTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> impExpIndCd = createComparable("impExpIndCd", Character.class);

    public final StringPath messageId = createString("messageId");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath sourceNm = createString("sourceNm");

    public final StringPath sourceTypeCd = createString("sourceTypeCd");

    public final NumberPath<Long> sourceUid = createNumber("sourceUid", Long.class);

    public final StringPath targetTypeCd = createString("targetTypeCd");

    public final NumberPath<Long> targetUid = createNumber("targetUid", Long.class);

    public QEdxActivityLog(String variable) {
        super(EdxActivityLog.class, forVariable(variable));
    }

    public QEdxActivityLog(Path<? extends EdxActivityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEdxActivityLog(PathMetadata metadata) {
        super(EdxActivityLog.class, metadata);
    }

}

