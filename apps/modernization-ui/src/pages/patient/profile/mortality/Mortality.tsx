import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientMortality, useUpdateMortalityMutation } from 'generated/graphql/schema';
import { useFindPatientProfileMortality } from './useFindPatientProfileMortality';
import { Data, EditableCard } from 'components/EditableCard';
import { externalizeDateTime, internalizeDate } from 'date';
import { MortalityEntry, MortalityForm } from './MortalityForm';
import { maybeDescription, maybeId } from '../coded';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

const initialEntry = {
    asOf: null,
    deceased: '',
    deceasedOn: null,
    city: '',
    state: null,
    county: null,
    country: null
};

const asView = (mortality?: PatientMortality | null): Data[] => [
    { title: 'As of:', text: internalizeDate(mortality?.asOf) || '' },
    { title: 'Is the patient deceased:', text: maybeDescription(mortality?.deceased) || '' },
    { title: 'Date of death:', text: internalizeDate(mortality?.deceasedOn) || '' },
    { title: 'City of death:', text: mortality?.city || '' },
    { title: 'State of death:', text: maybeDescription(mortality?.state) || '' },
    { title: 'County of death:', text: maybeDescription(mortality?.county) || '' },
    { title: 'Country of death:', text: maybeDescription(mortality?.country) || '' }
];

const asEntry = (mortality?: PatientMortality | null): MortalityEntry => ({
    ...initialEntry,
    asOf: internalizeDate(mortality?.asOf),
    deceased: maybeId(mortality?.deceased),
    deceasedTime: internalizeDate(mortality?.deceasedOn),
    cityOfDeath: mortality?.city,
    stateOfDeath: maybeId(mortality?.state),
    countyOfDeath: maybeId(mortality?.county),
    countryOfDeath: maybeId(mortality?.country)
});

export const Mortality = ({ patient }: PatientLabReportTableProps) => {
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<MortalityEntry>(initialEntry);

    const handleComplete = (data: FindPatientProfileQuery) => {
        setData(asView(data.findPatientProfile?.mortality));
        setEntry(asEntry(data.findPatientProfile?.mortality));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
    };

    const [getProfile, { refetch }] = useFindPatientProfileMortality({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient
                }
            });
        }
    }, [patient]);

    const [update] = useUpdateMortalityMutation();

    const onUpdate = (updated: any) => {
        update({
            variables: {
                input: {
                    ...updated,
                    asOf: externalizeDateTime(updated.asOf),
                    deceasedTime: externalizeDateTime(updated.deceasedTime),
                    patientId: patient
                }
            }
        }).then(handleUpdate);
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard title="Mortality" data={tableData} editing={editing} onEdit={() => isEditing(true)}>
                <MortalityForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
