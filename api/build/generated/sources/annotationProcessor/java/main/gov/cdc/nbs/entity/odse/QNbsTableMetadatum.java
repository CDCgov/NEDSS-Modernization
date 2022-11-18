package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNbsTableMetadatum is a Querydsl query type for NbsTableMetadatum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsTableMetadatum extends EntityPathBase<NbsTableMetadatum> {

    private static final long serialVersionUID = -1170820380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNbsTableMetadatum nbsTableMetadatum = new QNbsTableMetadatum("nbsTableMetadatum");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Integer> aggregateSeqNbr = createNumber("aggregateSeqNbr", Integer.class);

    public final StringPath datamartColumnNm = createString("datamartColumnNm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> indicatorSeqNbr = createNumber("indicatorSeqNbr", Integer.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath msgExcludeIndCd = createString("msgExcludeIndCd");

    public final QNbsAggregate nbsAggregateUid;

    public final QNbsIndicator nbsIndicatorUid;

    public final QNbsQuestion nbsQuestionUid;

    public final QNbsTable nbsTableUid;

    public final StringPath questionIdentifier = createString("questionIdentifier");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public QNbsTableMetadatum(String variable) {
        this(NbsTableMetadatum.class, forVariable(variable), INITS);
    }

    public QNbsTableMetadatum(Path<? extends NbsTableMetadatum> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNbsTableMetadatum(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNbsTableMetadatum(PathMetadata metadata, PathInits inits) {
        this(NbsTableMetadatum.class, metadata, inits);
    }

    public QNbsTableMetadatum(Class<? extends NbsTableMetadatum> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nbsAggregateUid = inits.isInitialized("nbsAggregateUid") ? new QNbsAggregate(forProperty("nbsAggregateUid"), inits.get("nbsAggregateUid")) : null;
        this.nbsIndicatorUid = inits.isInitialized("nbsIndicatorUid") ? new QNbsIndicator(forProperty("nbsIndicatorUid"), inits.get("nbsIndicatorUid")) : null;
        this.nbsQuestionUid = inits.isInitialized("nbsQuestionUid") ? new QNbsQuestion(forProperty("nbsQuestionUid")) : null;
        this.nbsTableUid = inits.isInitialized("nbsTableUid") ? new QNbsTable(forProperty("nbsTableUid")) : null;
    }

}

