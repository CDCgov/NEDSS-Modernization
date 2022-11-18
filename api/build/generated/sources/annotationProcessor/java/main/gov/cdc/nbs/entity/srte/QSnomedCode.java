package gov.cdc.nbs.entity.srte;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnomedCode is a Querydsl query type for SnomedCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnomedCode extends EntityPathBase<SnomedCode> {

    private static final long serialVersionUID = -162691629L;

    public static final QSnomedCode snomedCode = new QSnomedCode("snomedCode");

    public final SetPath<CodeValueClinical, QCodeValueClinical> codeValueClinicals = this.<CodeValueClinical, QCodeValueClinical>createSet("codeValueClinicals", CodeValueClinical.class, QCodeValueClinical.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.Instant> effectiveFromTime = createDateTime("effectiveFromTime", java.time.Instant.class);

    public final DateTimePath<java.time.Instant> effectiveToTime = createDateTime("effectiveToTime", java.time.Instant.class);

    public final StringPath id = createString("id");

    public final SetPath<LabResultSnomed, QLabResultSnomed> labResultSnomeds = this.<LabResultSnomed, QLabResultSnomed>createSet("labResultSnomeds", LabResultSnomed.class, QLabResultSnomed.class, PathInits.DIRECT2);

    public final SetPath<LoincSnomedCondition, QLoincSnomedCondition> loincSnomedConditions = this.<LoincSnomedCondition, QLoincSnomedCondition>createSet("loincSnomedConditions", LoincSnomedCondition.class, QLoincSnomedCondition.class, PathInits.DIRECT2);

    public final NumberPath<Integer> nbsUid = createNumber("nbsUid", Integer.class);

    public final ComparablePath<Character> paDerivationExcludeCd = createComparable("paDerivationExcludeCd", Character.class);

    public final SetPath<SnomedCondition, QSnomedCondition> snomedConditions = this.<SnomedCondition, QSnomedCondition>createSet("snomedConditions", SnomedCondition.class, QSnomedCondition.class, PathInits.DIRECT2);

    public final StringPath snomedDescTxt = createString("snomedDescTxt");

    public final StringPath sourceConceptId = createString("sourceConceptId");

    public final StringPath sourceVersionId = createString("sourceVersionId");

    public final ComparablePath<Character> statusCd = createComparable("statusCd", Character.class);

    public final DateTimePath<java.time.Instant> statusTime = createDateTime("statusTime", java.time.Instant.class);

    public QSnomedCode(String variable) {
        super(SnomedCode.class, forVariable(variable));
    }

    public QSnomedCode(Path<? extends SnomedCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSnomedCode(PathMetadata metadata) {
        super(SnomedCode.class, metadata);
    }

}

