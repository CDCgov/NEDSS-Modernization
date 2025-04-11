import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientMortality, useUpdatePatientMortalityMutation } from 'generated/graphql/schema';
import { useFindPatientProfileMortality } from './useFindPatientProfileMortality';
import { Data, EditableCard } from 'components/EditableCard';
import { externalizeDate, internalizeDate } from 'date';
import { MortalityForm } from './MortalityForm';
import { MortalityEntry } from './MortalityEntry';
import { maybeDescription, maybeId } from '../coded';
import { orNull } from 'utils';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { Patient } from '../Patient';

const initialEntry = {
    asOf: null,
    deceased: null,
    deceasedOn: null,
    city: null,
    state: null,
    county: null,
    country: null
};

const asView = (mortality?: PatientMortality | null): Data[] => [
    { title: 'As of:', text: internalizeDate(mortality?.asOf) },
    { title: 'Is the patient deceased:', text: maybeDescription(mortality?.deceased) },
    { title: 'Date of death:', text: internalizeDate(mortality?.deceasedOn) },
    { title: 'City of death:', text: mortality?.city },
    { title: 'State of death:', text: maybeDescription(mortality?.state) },
    { title: 'County of death:', text: maybeDescription(mortality?.county) },
    { title: 'Country of death:', text: maybeDescription(mortality?.country) }
];

const asEntry = (mortality: PatientMortality): MortalityEntry => ({
    asOf: internalizeDate(mortality?.asOf),
    deceased: maybeId(mortality?.deceased),
    deceasedOn: internalizeDate(mortality?.deceasedOn),
    city: orNull(mortality?.city),
    state: maybeId(mortality?.state),
    county: maybeId(mortality?.county),
    country: maybeId(mortality?.country)
});

type Props = {
    patient: Patient | undefined;
};

export const Mortality = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<MortalityEntry>(initialEntry);
    const { changed } = useProfileContext();

    const handleComplete = (data: FindPatientProfileQuery) => {
        const current = data.findPatientProfile?.mortality;
        setData(asView(current));

        const entry = current ? asEntry(current) : { ...initialEntry };

        setEntry(entry);
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
        showAlert({
            type: 'success',
            title: 'success',
            message: `Updated mortality`
        });
    };

    const [fetch, { refetch }] = useFindPatientProfileMortality({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            fetch({
                variables: {
                    patient: patient.id
                },
                notifyOnNetworkStatusChange: true
            });
        }
    }, [patient]);

    const [update] = useUpdatePatientMortalityMutation();

    const onUpdate = (updated: MortalityEntry) => {
        patient &&
            update({
                variables: {
                    input: {
                        ...updated,
                        patient: +patient.id,
                        asOf: externalizeDate(updated.asOf),
                        deceasedOn: externalizeDate(updated.deceasedOn)
                    }
                }
            }).then(() => {
                handleUpdate();
                changed();
            });
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard
                readOnly={patient?.status !== 'ACTIVE'}
                title="Mortality"
                data={tableData}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <MortalityForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
