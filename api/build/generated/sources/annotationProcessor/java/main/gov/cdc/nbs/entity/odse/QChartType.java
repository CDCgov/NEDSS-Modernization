package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChartType is a Querydsl query type for ChartType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChartType extends EntityPathBase<ChartType> {

    private static final long serialVersionUID = 1798427553L;

    public static final QChartType chartType = new QChartType("chartType");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath chartTypeCd = createString("chartTypeCd");

    public final StringPath chartTypeDescTxt = createString("chartTypeDescTxt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public QChartType(String variable) {
        super(ChartType.class, forVariable(variable));
    }

    public QChartType(Path<? extends ChartType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChartType(PathMetadata metadata) {
        super(ChartType.class, metadata);
    }

}

