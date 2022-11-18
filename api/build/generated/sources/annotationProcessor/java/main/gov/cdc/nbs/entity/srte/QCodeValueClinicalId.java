package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeValueClinicalId is a Querydsl query type for CodeValueClinicalId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCodeValueClinicalId extends BeanPath<CodeValueClinicalId> {

    private static final long serialVersionUID = -310706684L;

    public static final QCodeValueClinicalId codeValueClinicalId = new QCodeValueClinicalId("codeValueClinicalId");

    public final StringPath code = createString("code");

    public final StringPath codeSetNm = createString("codeSetNm");

    public final NumberPath<Short> seqNum = createNumber("seqNum", Short.class);

    public QCodeValueClinicalId(String variable) {
        super(CodeValueClinicalId.class, forVariable(variable));
    }

    public QCodeValueClinicalId(Path<? extends CodeValueClinicalId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeValueClinicalId(PathMetadata metadata) {
        super(CodeValueClinicalId.class, metadata);
    }

}

