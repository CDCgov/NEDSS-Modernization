package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNndActivityLogId is a Querydsl query type for NndActivityLogId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNndActivityLogId extends BeanPath<NndActivityLogId> {

    private static final long serialVersionUID = 1018755555L;

    public static final QNndActivityLogId nndActivityLogId = new QNndActivityLogId("nndActivityLogId");

    public final NumberPath<Short> nndActivityLogSeq = createNumber("nndActivityLogSeq", Short.class);

    public final NumberPath<Long> nndActivityLogUid = createNumber("nndActivityLogUid", Long.class);

    public QNndActivityLogId(String variable) {
        super(NndActivityLogId.class, forVariable(variable));
    }

    public QNndActivityLogId(Path<? extends NndActivityLogId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNndActivityLogId(PathMetadata metadata) {
        super(NndActivityLogId.class, metadata);
    }

}

