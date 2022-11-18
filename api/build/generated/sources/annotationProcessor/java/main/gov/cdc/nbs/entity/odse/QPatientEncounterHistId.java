package gov.cdc.nbs.entity.odse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPatientEncounterHistId is a Querydsl query type for PatientEncounterHistId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPatientEncounterHistId extends BeanPath<PatientEncounterHistId> {

    private static final long serialVersionUID = 1200015458L;

    public static final QPatientEncounterHistId patientEncounterHistId = new QPatientEncounterHistId("patientEncounterHistId");

    public final NumberPath<Long> patientEncounterUid = createNumber("patientEncounterUid", Long.class);

    public final NumberPath<Short> versionCtrlNbr = createNumber("versionCtrlNbr", Short.class);

    public QPatientEncounterHistId(String variable) {
        super(PatientEncounterHistId.class, forVariable(variable));
    }

    public QPatientEncounterHistId(Path<? extends PatientEncounterHistId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPatientEncounterHistId(PathMetadata metadata) {
        super(PatientEncounterHistId.class, metadata);
    }

}

