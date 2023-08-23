package gov.cdc.nbs.patient.profile.vaccination;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QIntervention;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.event.investigation.association.AssociatedWith;
import gov.cdc.nbs.event.investigation.association.AssociatedWithTupleMapper;
import gov.cdc.nbs.provider.ProviderNameTupleMapper;

import java.time.Instant;
import java.util.Objects;

class PatientVaccinationTupleMapper {

    record Tables(
            QIntervention vaccination,
            QCodeValueGeneral vaccine,
            ProviderNameTupleMapper.Tables provider,
            AssociatedWithTupleMapper.Tables associatedWith) {

        Tables() {
            this(
                    QIntervention.intervention,
                    new QCodeValueGeneral("vaccine"),
                    new ProviderNameTupleMapper.Tables(QPersonName.personName),
                    new AssociatedWithTupleMapper.Tables(
                            QPublicHealthCase.publicHealthCase,
                            new QConditionCode("condition")));
        }
    }


    private final Tables tables;
    private final ProviderNameTupleMapper providerMapper;
    private final AssociatedWithTupleMapper associatedWithMapper;

    PatientVaccinationTupleMapper(final Tables tables) {
        this.tables = tables;
        this.providerMapper = new ProviderNameTupleMapper(tables.provider());
        this.associatedWithMapper = new AssociatedWithTupleMapper(tables.associatedWith());
    }

    PatientVaccination map(final Tuple tuple) {

        long identifier = Objects.requireNonNull(
                tuple.get(this.tables.vaccination().id),
                "A vaccination identifier is required.");

        Instant createdOn = tuple.get(this.tables.vaccination().addTime);

        String provider = this.providerMapper.map(tuple);

        Instant administeredOn = tuple.get(this.tables.vaccination().activityFromTime);

        String administered = tuple.get(this.tables.vaccine().codeShortDescTxt);
        String event = tuple.get(this.tables.vaccination().localId);

        AssociatedWith associatedWith = this.associatedWithMapper.maybeMap(tuple)
                .orElse(null);

        return new PatientVaccination(
                identifier,
                createdOn,
                provider,
                administeredOn,
                administered,
                event,
                associatedWith);
    }


}
