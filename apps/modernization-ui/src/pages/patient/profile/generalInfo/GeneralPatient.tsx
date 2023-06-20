import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientGeneral, useUpdatePatientGeneralInfoMutation } from 'generated/graphql/schema';
import { useFindPatientProfileGeneral } from './useFindPatientProfileGeneral';
import { externalizeDateTime, internalizeDate } from 'date';
import { maybeDescription, maybeId } from '../coded';
import { Data, EditableCard } from 'components/EditableCard';
import { GeneralInformationEntry, GeneralPatientInformationForm } from './GeneralInformationForm';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

const initialEntry = {
    asOf: null,
    maritalStatus: '',
    maternalMaidenName: null,
    adultsInHouse: '',
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
    ...initialEntry,
    asOf: internalizeDate(mortality?.asOf),
    maritalStatus: maybeId(mortality?.maritalStatus),
    maternalMaidenName: mortality?.maternalMaidenName,
    adultsInHouse: mortality?.adultsInHouse?.toString(),
    childrenInHouse: mortality?.childrenInHouse?.toString(),
    occupation: maybeId(mortality?.occupation),
    educationLevel: maybeId(mortality?.educationLevel),
    primaryLanguage: maybeId(mortality?.primaryLanguage),
    speaksEnglish: maybeId(mortality?.speaksEnglish),
    stateHIVCase: mortality?.stateHIVCase
});

export const GeneralPatient = ({ patient }: PatientLabReportTableProps) => {
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<GeneralInformationEntry>(initialEntry);

    const handleComplete = (data: FindPatientProfileQuery) => {
        setData(asView(data.findPatientProfile?.general));
        setEntry(asEntry(data.findPatientProfile?.general));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
    };

    const [getProfile, { refetch }] = useFindPatientProfileGeneral({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient
                }
            });
        }
    }, [patient]);

    const [update] = useUpdatePatientGeneralInfoMutation();

    const onUpdate = (updated: any) => {
        update({
            variables: {
                input: {
                    ...updated,
                    asOf: externalizeDateTime(updated.asOf),
                    patientId: patient
                }
            }
        }).then(handleUpdate);
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard
                title="General Patient Information"
                data={tableData}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <GeneralPatientInformationForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
