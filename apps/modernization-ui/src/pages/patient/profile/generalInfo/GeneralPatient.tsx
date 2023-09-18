import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { PatientGeneral, useUpdatePatientGeneralInfoMutation } from 'generated/graphql/schema';
import { PatientProfileGeneralResult, useFindPatientProfileGeneral } from './useFindPatientProfileGeneral';
import { externalizeDateTime, internalizeDate } from 'date';
import { maybeDescription, maybeId } from '../coded';
import { Data, EditableCard } from 'components/EditableCard';
import { GeneralInformationEntry, GeneralPatientInformationForm } from './GeneralInformationForm';
import { orNull } from 'utils/orNull';
import { useAlert } from 'alert/useAlert';
import { usePatientProfile } from '../usePatientProfile';
import { useParams } from 'react-router-dom';

const initialEntry = {
    asOf: null,
    maritalStatus: null,
    maternalMaidenName: null,
    adultsInHouse: null,
    childrenInHouse: null,
    occupation: null,
    educationLevel: null,
    primaryLanguage: null,
    speaksEnglish: null,
    stateHIVCase: null
};

const asView = (general?: PatientGeneral | null): Data[] => [
    { title: 'As of:', text: internalizeDate(general?.asOf) || '' },
    { title: 'Marital status:', text: maybeDescription(general?.maritalStatus) || '' },
    { title: 'Mother’s maiden name:', text: general?.maternalMaidenName || '' },
    { title: 'Number of adults in residence:', text: general?.adultsInHouse?.toString() || '' },
    {
        title: 'Number of children in residence:',
        text: general?.childrenInHouse?.toString() || ''
    },
    { title: 'Primary occupation:', text: maybeDescription(general?.occupation) || '' },
    {
        title: 'Highest level of education:',
        text: maybeDescription(general?.educationLevel) || ''
    },
    { title: 'Primary language:', text: maybeDescription(general?.primaryLanguage) || '' },
    { title: 'Speaks english:', text: maybeDescription(general?.speaksEnglish) || '' },
    { title: 'State HIV case ID:', text: general?.stateHIVCase || '' }
];

const asEntry = (mortality?: PatientGeneral | null): GeneralInformationEntry => ({
    asOf: internalizeDate(mortality?.asOf),
    maritalStatus: maybeId(mortality?.maritalStatus),
    maternalMaidenName: orNull(mortality?.maternalMaidenName),
    adultsInHouse: mortality?.adultsInHouse ?? null,
    childrenInHouse: mortality?.childrenInHouse ?? null,
    occupation: maybeId(mortality?.occupation),
    educationLevel: maybeId(mortality?.educationLevel),
    primaryLanguage: maybeId(mortality?.primaryLanguage),
    speaksEnglish: maybeId(mortality?.speaksEnglish),
    stateHIVCase: orNull(mortality?.stateHIVCase)
});

type Props = {
    patient: string;
};

export const GeneralPatient = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const { id } = useParams();
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<GeneralInformationEntry>(initialEntry);
    const profile = usePatientProfile(id);

    const handleComplete = (data: PatientProfileGeneralResult) => {
        setData(asView(data.findPatientProfile?.general));
        setEntry(asEntry(data.findPatientProfile?.general));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
        showAlert({
            type: 'success',
            header: 'success',
            message: `Updated General patient information`
        });
    };

    const [getProfile, { refetch }] = useFindPatientProfileGeneral({ onCompleted: handleComplete });

    useEffect(() => {
        getProfile({
            variables: {
                patient: patient
            }
        });
    }, [patient]);

    const [update] = useUpdatePatientGeneralInfoMutation();

    const onUpdate = (updated: GeneralInformationEntry) => {
        update({
            variables: {
                input: {
                    ...updated,
                    patient: +patient,
                    asOf: externalizeDateTime(updated.asOf)
                }
            }
        }).then(handleUpdate);
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard
                readOnly={profile?.patient?.status !== 'ACTIVE'}
                title="General Patient Information"
                data={tableData}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <GeneralPatientInformationForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
