package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QElrXrefId is a Querydsl query type for ElrXrefId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QElrXrefId extends BeanPath<ElrXrefId> {

    private static final long serialVersionUID = 610908787L;

    public static final QElrXrefId elrXrefId = new QElrXrefId("elrXrefId");

    public final StringPath fromCode = createString("fromCode");

    public final StringPath fromCodeSetNm = createString("fromCodeSetNm");

    public final NumberPath<Short> fromSeqNum = createNumber("fromSeqNum", Short.class);

    public final StringPath toCode = createString("toCode");

    public final StringPath toCodeSetNm = createString("toCodeSetNm");

    public final NumberPath<Short> toSeqNum = createNumber("toSeqNum", Short.class);

    public QElrXrefId(String variable) {
        super(ElrXrefId.class, forVariable(variable));
    }

    public QElrXrefId(Path<? extends ElrXrefId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QElrXrefId(PathMetadata metadata) {
        super(ElrXrefId.class, metadata);
    }

}

