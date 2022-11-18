package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataMigrationDetail is a Querydsl query type for DataMigrationDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataMigrationDetail extends EntityPathBase<DataMigrationDetail> {

    private static final long serialVersionUID = 46763230L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataMigrationDetail dataMigrationDetail = new QDataMigrationDetail("dataMigrationDetail");

    public final QDataMigrationBatch dataMigrationBatchUid;

    public final QDataMigrationRecord dataMigrationRecord;

    public final StringPath failedDetailTxt = createString("failedDetailTxt");

    public final StringPath failedReasonTxt = createString("failedReasonTxt");

    public final QDataMigrationDetailId id;

    public QDataMigrationDetail(String variable) {
        this(DataMigrationDetail.class, forVariable(variable), INITS);
    }

    public QDataMigrationDetail(Path<? extends DataMigrationDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataMigrationDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataMigrationDetail(PathMetadata metadata, PathInits inits) {
        this(DataMigrationDetail.class, metadata, inits);
    }

    public QDataMigrationDetail(Class<? extends DataMigrationDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dataMigrationBatchUid = inits.isInitialized("dataMigrationBatchUid") ? new QDataMigrationBatch(forProperty("dataMigrationBatchUid")) : null;
        this.dataMigrationRecord = inits.isInitialized("dataMigrationRecord") ? new QDataMigrationRecord(forProperty("dataMigrationRecord"), inits.get("dataMigrationRecord")) : null;
        this.id = inits.isInitialized("id") ? new QDataMigrationDetailId(forProperty("id")) : null;
    }

}

