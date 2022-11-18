package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodesetId is a Querydsl query type for CodesetId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCodesetId extends BeanPath<CodesetId> {

    private static final long serialVersionUID = 1697280418L;

    public static final QCodesetId codesetId = new QCodesetId("codesetId");

    public final StringPath classCd = createString("classCd");

    public final StringPath codeSetNm = createString("codeSetNm");

    public QCodesetId(String variable) {
        super(CodesetId.class, forVariable(variable));
    }

    public QCodesetId(Path<? extends CodesetId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodesetId(PathMetadata metadata) {
        super(CodesetId.class, metadata);
    }

}

