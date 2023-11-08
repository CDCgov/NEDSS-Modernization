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
import { useProfileContext } from '../ProfileContext';
import { Patient } from '../Patient';

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
    patient: Patient | undefined;
};

export const GeneralPatient = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const { changed } = useProfileContext();
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<GeneralInformationEntry>(initialEntry);

    const handleComplete = (data: PatientProfileGeneralResult) => {
        setData(asView(data.findPatientProfile?.general));
        setEntry(asEntry(data.findPatientProfile?.general));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
    };

    const [getProfile, { refetch }] = useFindPatientProfileGeneral({ onCompleted: handleComplete });

    useEffect(() => {
        patient &&
            getProfile({
                variables: {
                    patient: patient?.id
                },
                notifyOnNetworkStatusChange: true
            });
    }, [patient]);

    const [update] = useUpdatePatientGeneralInfoMutation();

    const onUpdate = (updated: GeneralInformationEntry) => {
        patient &&
            update({
                variables: {
                    input: {
                        ...updated,
                        patient: +patient?.id,
                        asOf: externalizeDateTime(updated.asOf)
                    }
                }
            })
                .then(handleUpdate)
                .then(() =>
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated General patient information`
                    })
                )
                .then(changed);
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard
                readOnly={patient?.status !== 'ACTIVE'}
                title="General patient information"
                data={tableData}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <GeneralPatientInformationForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
