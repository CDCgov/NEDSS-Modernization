package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataMigrationRecord is a Querydsl query type for DataMigrationRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataMigrationRecord extends EntityPathBase<DataMigrationRecord> {

    private static final long serialVersionUID = 447078622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataMigrationRecord dataMigrationRecord = new QDataMigrationRecord("dataMigrationRecord");

    public final StringPath caseIdTxt = createString("caseIdTxt");

    public final QDataMigrationBatch dataMigrationBatchUid;

    public final StringPath dataMigrationSts = createString("dataMigrationSts");

    public final StringPath failedRecordTxt = createString("failedRecordTxt");

    public final QDataMigrationRecordId id;

    public final StringPath subNm = createString("subNm");

    public QDataMigrationRecord(String variable) {
        this(DataMigrationRecord.class, forVariable(variable), INITS);
    }

    public QDataMigrationRecord(Path<? extends DataMigrationRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataMigrationRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataMigrationRecord(PathMetadata metadata, PathInits inits) {
        this(DataMigrationRecord.class, metadata, inits);
    }

    public QDataMigrationRecord(Class<? extends DataMigrationRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dataMigrationBatchUid = inits.isInitialized("dataMigrationBatchUid") ? new QDataMigrationBatch(forProperty("dataMigrationBatchUid")) : null;
        this.id = inits.isInitialized("id") ? new QDataMigrationRecordId(forProperty("id")) : null;
    }

}

