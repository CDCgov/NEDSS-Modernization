package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeValueGeneralId is a Querydsl query type for CodeValueGeneralId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCodeValueGeneralId extends BeanPath<CodeValueGeneralId> {

    private static final long serialVersionUID = 1374284525L;

    public static final QCodeValueGeneralId codeValueGeneralId = new QCodeValueGeneralId("codeValueGeneralId");

    public final StringPath code = createString("code");

    public final StringPath codeSetNm = createString("codeSetNm");

    public QCodeValueGeneralId(String variable) {
        super(CodeValueGeneralId.class, forVariable(variable));
    }

    public QCodeValueGeneralId(Path<? extends CodeValueGeneralId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeValueGeneralId(PathMetadata metadata) {
        super(CodeValueGeneralId.class, metadata);
    }

}

