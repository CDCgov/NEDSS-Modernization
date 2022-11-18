package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocalUidGenerator is a Querydsl query type for LocalUidGenerator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocalUidGenerator extends EntityPathBase<LocalUidGenerator> {

    private static final long serialVersionUID = -518727209L;

    public static final QLocalUidGenerator localUidGenerator = new QLocalUidGenerator("localUidGenerator");

    public final StringPath id = createString("id");

    public final NumberPath<Long> seedValueNbr = createNumber("seedValueNbr", Long.class);

    public final StringPath typeCd = createString("typeCd");

    public final StringPath uidPrefixCd = createString("uidPrefixCd");

    public final StringPath uidSuffixCd = createString("uidSuffixCd");

    public QLocalUidGenerator(String variable) {
        super(LocalUidGenerator.class, forVariable(variable));
    }

    public QLocalUidGenerator(Path<? extends LocalUidGenerator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocalUidGenerator(PathMetadata metadata) {
        super(LocalUidGenerator.class, metadata);
    }

}

