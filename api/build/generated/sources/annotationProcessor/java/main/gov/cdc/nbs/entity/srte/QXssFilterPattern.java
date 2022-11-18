package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QXssFilterPattern is a Querydsl query type for XssFilterPattern
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QXssFilterPattern extends EntityPathBase<XssFilterPattern> {

    private static final long serialVersionUID = -1701363602L;

    public static final QXssFilterPattern xssFilterPattern = new QXssFilterPattern("xssFilterPattern");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath flag = createString("flag");

    public final StringPath regExp = createString("regExp");

    public final StringPath statusCd = createString("statusCd");

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final NumberPath<Long> xssFilterPatternUid = createNumber("xssFilterPatternUid", Long.class);

    public QXssFilterPattern(String variable) {
        super(XssFilterPattern.class, forVariable(variable));
    }

    public QXssFilterPattern(Path<? extends XssFilterPattern> path) {
        super(path.getType(), path.getMetadata());
    }

    public QXssFilterPattern(PathMetadata metadata) {
        super(XssFilterPattern.class, metadata);
    }

}

