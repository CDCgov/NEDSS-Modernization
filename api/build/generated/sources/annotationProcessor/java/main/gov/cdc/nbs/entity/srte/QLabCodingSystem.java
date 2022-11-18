package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLabCodingSystem is a Querydsl query type for LabCodingSystem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLabCodingSystem extends EntityPathBase<LabCodingSystem> {

    private static final long serialVersionUID = -210932424L;

    public static final QLabCodingSystem labCodingSystem = new QLabCodingSystem("labCodingSystem");

    public final StringPath assigningAuthorityCd = createString("assigningAuthorityCd");

    public final StringPath assigningAuthorityDescTxt = createString("assigningAuthorityDescTxt");

    public final StringPath codeSystemDescTxt = createString("codeSystemDescTxt");

    public final StringPath codingSystemCd = createString("codingSystemCd");

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final ComparablePath<Character> electronicLabInd = createComparable("electronicLabInd", Character.class);

    public final StringPath id = createString("id");

    public final StringPath laboratorySystemDescTxt = createString("laboratorySystemDescTxt");

    public final SetPath<LabResult, QLabResult> labResults = this.<LabResult, QLabResult>createSet("labResults", LabResult.class, QLabResult.class, PathInits.DIRECT2);

    public final SetPath<LabTest, QLabTest> labTests = this.<LabTest, QLabTest>createSet("labTests", LabTest.class, QLabTest.class, PathInits.DIRECT2);

    public final NumberPath<Long> nbsUid = createNumber("nbsUid", Long.class);

    public QLabCodingSystem(String variable) {
        super(LabCodingSystem.class, forVariable(variable));
    }

    public QLabCodingSystem(Path<? extends LabCodingSystem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLabCodingSystem(PathMetadata metadata) {
        super(LabCodingSystem.class, metadata);
    }

}

