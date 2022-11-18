package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStandardXref is a Querydsl query type for StandardXref
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStandardXref extends EntityPathBase<StandardXref> {

    private static final long serialVersionUID = -1751156250L;

    public static final QStandardXref standardXref = new QStandardXref("standardXref");

    public final StringPath fromCode = createString("fromCode");

    public final StringPath fromCodeDescTxt = createString("fromCodeDescTxt");

    public final StringPath fromCodeSetNm = createString("fromCodeSetNm");

    public final NumberPath<Short> fromSeqNum = createNumber("fromSeqNum", Short.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath toCode = createString("toCode");

    public final StringPath toCodeDescTxt = createString("toCodeDescTxt");

    public final StringPath toCodeSetNm = createString("toCodeSetNm");

    public final StringPath toCodeSystemCd = createString("toCodeSystemCd");

    public final NumberPath<Short> toSeqNum = createNumber("toSeqNum", Short.class);

    public QStandardXref(String variable) {
        super(StandardXref.class, forVariable(variable));
    }

    public QStandardXref(Path<? extends StandardXref> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStandardXref(PathMetadata metadata) {
        super(StandardXref.class, metadata);
    }

}

