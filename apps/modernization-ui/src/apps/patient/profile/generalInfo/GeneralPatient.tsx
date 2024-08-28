import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { PatientGeneral, useUpdatePatientGeneralInfoMutation } from 'generated/graphql/schema';
import { PatientProfileGeneralResult, useFindPatientProfileGeneral } from './useFindPatientProfileGeneral';
import { externalizeDateTime, internalizeDate } from 'date';
import { orNull } from 'utils/orNull';
import { interalize } from 'sensitive';
import { useAlert } from 'alert/useAlert';
import { Data, EditableCard } from 'components/EditableCard';
import { GeneralPatientInformationForm } from './GeneralInformationForm';
import { maybeDescription, maybeId } from 'apps/patient/profile/coded';
import { useProfileContext } from 'apps/patient/profile/ProfileContext';
import { Patient } from 'apps/patient/profile/Patient';
import { usePatientProfilePermissions } from 'apps/patient/profile/permission';
import { GeneralInformationEntry } from './GeneralInformationEntry';

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

const asView = (hivAccess: boolean, general?: PatientGeneral | null): Data[] => {
    const values = [
        { title: 'As of:', text: internalizeDate(general?.asOf) },
        { title: 'Marital status:', text: maybeDescription(general?.maritalStatus) },
        { title: 'Mother’s maiden name:', text: general?.maternalMaidenName },
        { title: 'Number of adults in residence:', text: general?.adultsInHouse?.toString() },
        {
            title: 'Number of children in residence:',
            text: general?.childrenInHouse?.toString()
        },
        { title: 'Primary occupation:', text: maybeDescription(general?.occupation) },
        {
            title: 'Highest level of education:',
            text: maybeDescription(general?.educationLevel)
        },
        { title: 'Primary language:', text: maybeDescription(general?.primaryLanguage) },
        { title: 'Speaks english:', text: maybeDescription(general?.speaksEnglish) }
    ];

    if (hivAccess) {
        values.push({ title: 'State HIV case ID:', text: interalize(general?.stateHIVCase) });
    }

    return values;
};

const asEntry = (general?: PatientGeneral | null): GeneralInformationEntry => ({
    asOf: internalizeDate(general?.asOf),
    maritalStatus: maybeId(general?.maritalStatus),
    maternalMaidenName: orNull(general?.maternalMaidenName),
    adultsInHouse: general?.adultsInHouse ?? null,
    childrenInHouse: general?.childrenInHouse ?? null,
    occupation: maybeId(general?.occupation),
    educationLevel: maybeId(general?.educationLevel),
    primaryLanguage: maybeId(general?.primaryLanguage),
    speaksEnglish: maybeId(general?.speaksEnglish),
    stateHIVCase: interalize(general?.stateHIVCase)
});

type Props = {
    patient: Patient | undefined;
};

export const GeneralPatient = ({ patient }: Props) => {
    const { hivAccess } = usePatientProfilePermissions();

    const { showAlert } = useAlert();
    const { changed } = useProfileContext();
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<GeneralInformationEntry>(initialEntry);

    const handleComplete = (data: PatientProfileGeneralResult) => {
        setData(asView(hivAccess, data.findPatientProfile?.general));
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
