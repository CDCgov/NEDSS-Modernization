package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsTable is a Querydsl query type for NbsTable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsTable extends EntityPathBase<NbsTable> {

    private static final long serialVersionUID = 674785606L;

    public static final QNbsTable nbsTable = new QNbsTable("nbsTable");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath nbsTableNm = createString("nbsTableNm");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public QNbsTable(String variable) {
        super(NbsTable.class, forVariable(variable));
    }

    public QNbsTable(Path<? extends NbsTable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsTable(PathMetadata metadata) {
        super(NbsTable.class, metadata);
    }

}

