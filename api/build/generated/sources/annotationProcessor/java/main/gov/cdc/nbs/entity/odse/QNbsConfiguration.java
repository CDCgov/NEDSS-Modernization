package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNbsConfiguration is a Querydsl query type for NbsConfiguration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNbsConfiguration extends EntityPathBase<NbsConfiguration> {

    private static final long serialVersionUID = -193811858L;

    public static final QNbsConfiguration nbsConfiguration = new QNbsConfiguration("nbsConfiguration");

    public final StringPath addRelease = createString("addRelease");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final StringPath adminComment = createString("adminComment");

    public final StringPath category = createString("category");

    public final StringPath configValue = createString("configValue");

    public final StringPath defaultValue = createString("defaultValue");

    public final StringPath descTxt = createString("descTxt");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath shortName = createString("shortName");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public final StringPath systemUsage = createString("systemUsage");

    public final StringPath validValues = createString("validValues");

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QNbsConfiguration(String variable) {
        super(NbsConfiguration.class, forVariable(variable));
    }

    public QNbsConfiguration(Path<? extends NbsConfiguration> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNbsConfiguration(PathMetadata metadata) {
        super(NbsConfiguration.class, metadata);
    }

}

