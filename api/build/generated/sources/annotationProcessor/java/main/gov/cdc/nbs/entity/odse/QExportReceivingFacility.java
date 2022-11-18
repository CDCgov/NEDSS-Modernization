package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExportReceivingFacility is a Querydsl query type for ExportReceivingFacility
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExportReceivingFacility extends EntityPathBase<ExportReceivingFacility> {

    private static final long serialVersionUID = 1989889016L;

    public static final QExportReceivingFacility exportReceivingFacility = new QExportReceivingFacility("exportReceivingFacility");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComment = createString("adminComment");

    public final ComparablePath<Character> allowTransferIndCd = createComparable("allowTransferIndCd", Character.class);

    public final StringPath encrypt = createString("encrypt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jurDeriveIndCd = createString("jurDeriveIndCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final StringPath lastChgUserId = createString("lastChgUserId");

    public final StringPath messageRecipient = createString("messageRecipient");

    public final NumberPath<Short> priorityInt = createNumber("priorityInt", Short.class);

    public final StringPath publicKeyLdapaddress = createString("publicKeyLdapaddress");

    public final StringPath publicKeyLdapbasedn = createString("publicKeyLdapbasedn");

    public final StringPath publicKeyLdapdn = createString("publicKeyLdapdn");

    public final ComparablePath<Character> receivingIndCd = createComparable("receivingIndCd", Character.class);

    public final StringPath receivingSystemDescTxt = createString("receivingSystemDescTxt");

    public final StringPath receivingSystemNm = createString("receivingSystemNm");

    public final StringPath receivingSystemOid = createString("receivingSystemOid");

    public final StringPath receivingSystemOwner = createString("receivingSystemOwner");

    public final StringPath receivingSystemOwnerOid = createString("receivingSystemOwnerOid");

    public final StringPath receivingSystemShortNm = createString("receivingSystemShortNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final ComparablePath<Character> sendingIndCd = createComparable("sendingIndCd", Character.class);

    public final StringPath signature = createString("signature");

    public final StringPath typeCd = createString("typeCd");

    public QExportReceivingFacility(String variable) {
        super(ExportReceivingFacility.class, forVariable(variable));
    }

    public QExportReceivingFacility(Path<? extends ExportReceivingFacility> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExportReceivingFacility(PathMetadata metadata) {
        super(ExportReceivingFacility.class, metadata);
    }

}

