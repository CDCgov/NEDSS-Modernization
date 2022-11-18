package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataSourceCodedata is a Querydsl query type for DataSourceCodedata
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataSourceCodedata extends EntityPathBase<DataSourceCodedata> {

    private static final long serialVersionUID = -1738958509L;

    public static final QDataSourceCodedata dataSourceCodedata = new QDataSourceCodedata("dataSourceCodedata");

    public final StringPath codeDescCd = createString("codeDescCd");

    public final StringPath codesetName = createString("codesetName");

    public final StringPath columnName = createString("columnName");

    public final StringPath dataSourceName = createString("dataSourceName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDataSourceCodedata(String variable) {
        super(DataSourceCodedata.class, forVariable(variable));
    }

    public QDataSourceCodedata(Path<? extends DataSourceCodedata> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataSourceCodedata(PathMetadata metadata) {
        super(DataSourceCodedata.class, metadata);
    }

}

