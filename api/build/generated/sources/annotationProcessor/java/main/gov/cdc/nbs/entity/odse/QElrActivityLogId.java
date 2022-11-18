package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QElrActivityLogId is a Querydsl query type for ElrActivityLogId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QElrActivityLogId extends BeanPath<ElrActivityLogId> {

    private static final long serialVersionUID = -184393924L;

    public static final QElrActivityLogId elrActivityLogId = new QElrActivityLogId("elrActivityLogId");

    public final NumberPath<Short> elrActivityLogSeq = createNumber("elrActivityLogSeq", Short.class);

    public final NumberPath<Long> msgObservationUid = createNumber("msgObservationUid", Long.class);

    public QElrActivityLogId(String variable) {
        super(ElrActivityLogId.class, forVariable(variable));
    }

    public QElrActivityLogId(Path<? extends ElrActivityLogId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QElrActivityLogId(PathMetadata metadata) {
        super(ElrActivityLogId.class, metadata);
    }

}

