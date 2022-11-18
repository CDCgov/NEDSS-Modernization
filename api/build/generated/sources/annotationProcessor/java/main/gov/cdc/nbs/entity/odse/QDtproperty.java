package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDtproperty is a Querydsl query type for Dtproperty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDtproperty extends EntityPathBase<Dtproperty> {

    private static final long serialVersionUID = -239980516L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDtproperty dtproperty = new QDtproperty("dtproperty");

    public final QDtpropertyId id;

    public final ArrayPath<byte[], Byte> lvalue = createArray("lvalue", byte[].class);

    public final NumberPath<Integer> objectid = createNumber("objectid", Integer.class);

    public final StringPath uvalue = createString("uvalue");

    public final StringPath value = createString("value");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QDtproperty(String variable) {
        this(Dtproperty.class, forVariable(variable), INITS);
    }

    public QDtproperty(Path<? extends Dtproperty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDtproperty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDtproperty(PathMetadata metadata, PathInits inits) {
        this(Dtproperty.class, metadata, inits);
    }

    public QDtproperty(Class<? extends Dtproperty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QDtpropertyId(forProperty("id")) : null;
    }

}

