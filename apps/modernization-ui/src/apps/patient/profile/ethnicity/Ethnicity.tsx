import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientEthnicity, useUpdateEthnicityMutation } from 'generated/graphql/schema';
import { internalizeDate } from 'date';
import { useFindPatientProfileEthnicity } from './useFindPatientProfileMortality';
import { Data, EditableCard } from 'components/EditableCard';
import { maybeDescription, maybeDescriptions, maybeId, maybeIds } from 'apps/patient/profile/coded';
import { EthnicityForm } from './EthnicityForm';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { Patient } from '../Patient';
import { EthnicityEntry } from './EthnicityEntry';

type Props = {
    patient: Patient | undefined;
};

const initialEntry = {
    asOf: null,
    ethnicGroup: null,
    unknownReason: null,
    detailed: []
};

const asView = (ethnicity?: PatientEthnicity | null): Data[] => [
    {
        title: 'As of:',
        text: internalizeDate(ethnicity?.asOf) || ''
    },
    { title: 'Ethnicity:', text: maybeDescription(ethnicity?.ethnicGroup) || '' },
    {
        title: 'Spanish origin:',
        text: maybeDescriptions(ethnicity?.detailed).join(' | ')
    },
    {
        title: 'Reasons unknown:',
        text: ethnicity?.unknownReason?.description || ''
    }
];

const asEntry = (ethnicity?: PatientEthnicity | null): EthnicityEntry => ({
    ...initialEntry,
    asOf: internalizeDate(ethnicity?.asOf),
    ethnicGroup: maybeId(ethnicity?.ethnicGroup),
    unknownReason: maybeId(ethnicity?.unknownReason),
    detailed: maybeIds(ethnicity?.detailed)
});
export const Ethnicity = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const { changed } = useProfileContext();
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<EthnicityEntry>(initialEntry);
    const [editing, isEditing] = useState<boolean>(false);

    const handleComplete = (result: FindPatientProfileQuery) => {
        const current = result.findPatientProfile?.ethnicity;

        setData(asView(current));
        setEntry(asEntry(current));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
        showAlert({
            type: 'success',
            title: 'success',
            message: `Updated ethnicity`
        });
    };

    const [fetchProfile, { refetch }] = useFindPatientProfileEthnicity({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            fetchProfile({
                variables: {
                    patient: patient.id
                },
                notifyOnNetworkStatusChange: true
            });
        }
    }, [patient]);

    const [update] = useUpdateEthnicityMutation();

    const onUpdate = (updated: EthnicityEntry) => {
        patient &&
            update({
                variables: {
                    input: {
                        ...updated,
                        patient: patient.id
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
                title="Ethnicity"
                data={tableData}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <EthnicityForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
