package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDataSource is a Querydsl query type for DataSource
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDataSource extends EntityPathBase<DataSource> {

    private static final long serialVersionUID = -586534372L;

    public static final QDataSource dataSource = new QDataSource("dataSource");

    public final NumberPath<Short> columnMaxLen = createNumber("columnMaxLen", Short.class);

    public final ComparablePath<Character> conditionSecurity = createComparable("conditionSecurity", Character.class);

    public final StringPath dataSourceName = createString("dataSourceName");

    public final StringPath dataSourceTitle = createString("dataSourceTitle");

    public final StringPath dataSourceTypeCode = createString("dataSourceTypeCode");

    public final StringPath descTxt = createString("descTxt");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<Character> jurisdictionSecurity = createComparable("jurisdictionSecurity", Character.class);

    public final StringPath orgAccessPermis = createString("orgAccessPermis");

    public final StringPath progAreaAccessPermis = createString("progAreaAccessPermis");

    public final ComparablePath<Character> reportingFacilitySecurity = createComparable("reportingFacilitySecurity", Character.class);

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QDataSource(String variable) {
        super(DataSource.class, forVariable(variable));
    }

    public QDataSource(Path<? extends DataSource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataSource(PathMetadata metadata) {
        super(DataSource.class, metadata);
    }

}

