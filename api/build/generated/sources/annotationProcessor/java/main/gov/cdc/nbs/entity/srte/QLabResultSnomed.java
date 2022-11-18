package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabResultSnomed is a Querydsl query type for LabResultSnomed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabResultSnomed extends EntityPathBase<LabResultSnomed> {

    private static final long serialVersionUID = -1195658924L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabResultSnomed labResultSnomed = new QLabResultSnomed("labResultSnomed");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QLabResultSnomedId id;

    public final QLabResult labResult;

    public final QSnomedCode snomedCd;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QLabResultSnomed(String variable) {
        this(LabResultSnomed.class, forVariable(variable), INITS);
    }

    public QLabResultSnomed(Path<? extends LabResultSnomed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabResultSnomed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabResultSnomed(PathMetadata metadata, PathInits inits) {
        this(LabResultSnomed.class, metadata, inits);
    }

    public QLabResultSnomed(Class<? extends LabResultSnomed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QLabResultSnomedId(forProperty("id")) : null;
        this.labResult = inits.isInitialized("labResult") ? new QLabResult(forProperty("labResult"), inits.get("labResult")) : null;
        this.snomedCd = inits.isInitialized("snomedCd") ? new QSnomedCode(forProperty("snomedCd")) : null;
    }

}

