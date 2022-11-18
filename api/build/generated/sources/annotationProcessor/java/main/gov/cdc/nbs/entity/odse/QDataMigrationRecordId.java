package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataMigrationRecordId is a Querydsl query type for DataMigrationRecordId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDataMigrationRecordId extends BeanPath<DataMigrationRecordId> {

    private static final long serialVersionUID = 145828505L;

    public static final QDataMigrationRecordId dataMigrationRecordId = new QDataMigrationRecordId("dataMigrationRecordId");

    public final NumberPath<Long> dataMigrationBatchUid = createNumber("dataMigrationBatchUid", Long.class);

    public final NumberPath<Long> dataMigrationRecordUid = createNumber("dataMigrationRecordUid", Long.class);

    public QDataMigrationRecordId(String variable) {
        super(DataMigrationRecordId.class, forVariable(variable));
    }

    public QDataMigrationRecordId(Path<? extends DataMigrationRecordId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataMigrationRecordId(PathMetadata metadata) {
        super(DataMigrationRecordId.class, metadata);
    }

}

