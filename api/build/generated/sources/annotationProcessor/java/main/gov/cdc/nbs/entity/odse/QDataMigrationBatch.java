package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataMigrationBatch is a Querydsl query type for DataMigrationBatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataMigrationBatch extends EntityPathBase<DataMigrationBatch> {

    private static final long serialVersionUID = -1940120307L;

    public static final QDataMigrationBatch dataMigrationBatch = new QDataMigrationBatch("dataMigrationBatch");

    public final DateTimePath<java.time.Instant> batchEndTime = createDateTime("batchEndTime", java.time.Instant.class);

    public final StringPath batchNm = createString("batchNm");

    public final DateTimePath<java.time.Instant> batchStartTime = createDateTime("batchStartTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Short> recordsFailedNbr = createNumber("recordsFailedNbr", Short.class);

    public final NumberPath<Short> recordsMigratedNbr = createNumber("recordsMigratedNbr", Short.class);

    public final NumberPath<Short> recordsToMigrateNbr = createNumber("recordsToMigrateNbr", Short.class);

    public QDataMigrationBatch(String variable) {
        super(DataMigrationBatch.class, forVariable(variable));
    }

    public QDataMigrationBatch(Path<? extends DataMigrationBatch> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataMigrationBatch(PathMetadata metadata) {
        super(DataMigrationBatch.class, metadata);
    }

}

