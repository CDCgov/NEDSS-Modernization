package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPhysicalLocator is a Querydsl query type for PhysicalLocator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPhysicalLocator extends EntityPathBase<PhysicalLocator> {

    private static final long serialVersionUID = 2068539496L;

    public static final QPhysicalLocator physicalLocator = new QPhysicalLocator("physicalLocator");

    public final StringPath addReasonCd = createString("addReasonCd");

    public final DateTimePath<java.time.Instant> addTime = createDateTime("addTime", java.time.Instant.class);

    public final NumberPath<Long> addUserId = createNumber("addUserId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ArrayPath<byte[], Byte> imageTxt = createArray("imageTxt", byte[].class);

    public final StringPath lastChgReasonCd = createString("lastChgReasonCd");

    public final DateTimePath<java.time.Instant> lastChgTime = createDateTime("lastChgTime", java.time.Instant.class);

    public final NumberPath<Long> lastChgUserId = createNumber("lastChgUserId", Long.class);

    public final StringPath locatorTxt = createString("locatorTxt");

    public final StringPath recordStatusCd = createString("recordStatusCd");

    public final DateTimePath<java.time.Instant> recordStatusTime = createDateTime("recordStatusTime", java.time.Instant.class);

    public final StringPath userAffiliationTxt = createString("userAffiliationTxt");

    public QPhysicalLocator(String variable) {
        super(PhysicalLocator.class, forVariable(variable));
    }

    public QPhysicalLocator(Path<? extends PhysicalLocator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhysicalLocator(PathMetadata metadata) {
        super(PhysicalLocator.class, metadata);
    }

}

