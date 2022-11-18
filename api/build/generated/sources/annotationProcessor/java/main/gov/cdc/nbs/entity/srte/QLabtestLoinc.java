package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabtestLoinc is a Querydsl query type for LabtestLoinc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabtestLoinc extends EntityPathBase<LabtestLoinc> {

    private static final long serialVersionUID = -454958198L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLabtestLoinc labtestLoinc = new QLabtestLoinc("labtestLoinc");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final QLabtestLoincId id;

    public final QLabTest labTest;

    public final QLoincCode loincCd;

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QLabtestLoinc(String variable) {
        this(LabtestLoinc.class, forVariable(variable), INITS);
    }

    public QLabtestLoinc(Path<? extends LabtestLoinc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLabtestLoinc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLabtestLoinc(PathMetadata metadata, PathInits inits) {
        this(LabtestLoinc.class, metadata, inits);
    }

    public QLabtestLoinc(Class<? extends LabtestLoinc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QLabtestLoincId(forProperty("id")) : null;
        this.labTest = inits.isInitialized("labTest") ? new QLabTest(forProperty("labTest"), inits.get("labTest")) : null;
        this.loincCd = inits.isInitialized("loincCd") ? new QLoincCode(forProperty("loincCd"), inits.get("loincCd")) : null;
    }

}

