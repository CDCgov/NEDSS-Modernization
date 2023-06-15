import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, useUpdatePatientSexBirthMutation } from 'generated/graphql/schema';
import { useFindPatientProfileBirth } from './useFindPatientProfileBirth';
import { internalizeDate, externalizeDate, externalizeDateTime } from 'date';
import { Data, EditableCard } from 'components/EditableCard';
import { maybeDescription, maybeId } from '../coded';
import { SexAndEntry, SexBirthForm } from './SexBirthForm';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

const initialEntry = {
    additionalGender: '',
    ageReportedTime: null,
    asOf: null,
    birthCity: '',
    birthCntry: '',
    birthGender: null,
    birthOrderNbr: null,
    birthState: '',
    currentAge: null,
    currentGender: null,
    dateOfBirth: null,
    multipleBirth: '',
    sexUnknown: '',
    transGenderInfo: ''
};

const asView = (sexAndBirth?: FindPatientProfileQuery['findPatientProfile'] | null): Data[] => [
    {
        title: 'As of:',
        text: internalizeDate(sexAndBirth?.birth?.asOf) || ''
    },
    { title: 'Current age:', text: sexAndBirth?.birth?.age?.toString() || '' },
    { title: 'Current sex:', text: sexAndBirth?.gender?.current?.description || '' },
    { title: 'Unknown reason:', text: maybeDescription(sexAndBirth?.gender?.unknownReason) || '' },
    { title: 'Transgender information:', text: maybeDescription(sexAndBirth?.gender?.preferred) || '' },
    { title: 'Additional gender:', text: sexAndBirth?.gender?.additional || '' },
    { title: 'Birth sex:', text: maybeDescription(sexAndBirth?.gender?.birth) || '' },
    { title: 'Multiple birth:', text: maybeDescription(sexAndBirth?.birth?.multipleBirth) || '' },
    { title: 'Birth order:', text: sexAndBirth?.birth?.birthOrder?.toString() || '' },
    { title: 'Birth city:', text: sexAndBirth?.birth?.city || '' },
    { title: 'Birth state:', text: maybeDescription(sexAndBirth?.birth?.state) || '' },
    { title: 'Birth county:', text: maybeDescription(sexAndBirth?.birth?.county) || '' },
    { title: 'Birth country:', text: maybeDescription(sexAndBirth?.birth?.country) || '' }
];

const asEntry = (sexAndBirth?: FindPatientProfileQuery['findPatientProfile'] | null): SexAndEntry => ({
    ...initialEntry,
    asOf: internalizeDate(sexAndBirth?.birth?.asOf),
    additionalGender: sexAndBirth?.gender?.additional,
    birthCity: sexAndBirth?.birth?.city,
    birthCntry: maybeId(sexAndBirth?.birth?.country),
    birthGender: maybeId(sexAndBirth?.gender?.current),
    birthOrderNbr: sexAndBirth?.birth?.birthOrder,
    birthState: maybeId(sexAndBirth?.birth?.state),
    currentAge: sexAndBirth?.birth?.age,
    currentGender: maybeId(sexAndBirth?.gender?.current),
    dateOfBirth: internalizeDate(sexAndBirth?.birth?.bornOn),
    multipleBirth: maybeId(sexAndBirth?.birth?.multipleBirth),
    sexUnknown: maybeId(sexAndBirth?.gender?.unknownReason),
    transGenderInfo: maybeId(sexAndBirth?.gender?.preferred)
});

export const SexBirth = ({ patient }: PatientLabReportTableProps) => {
    const [editing, isEditing] = useState<boolean>(false);
    const [tableData, setData] = useState<Data[]>([]);
    const [entry, setEntry] = useState<SexAndEntry>(initialEntry);

    const handleComplete = (data: FindPatientProfileQuery) => {
        setData(asView(data.findPatientProfile));
        setEntry(asEntry(data.findPatientProfile));
    };

    const handleUpdate = () => {
        refetch();
        isEditing(false);
    };

    const [getProfile, { refetch }] = useFindPatientProfileBirth({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient
                }
            });
        }
    }, [patient]);

    const [update] = useUpdatePatientSexBirthMutation();

    const onUpdate = (updated: any) => {
        update({
            variables: {
                input: {
                    ...updated,
                    dateOfBirth: externalizeDate(updated?.dateOfBirth),
                    asOf: externalizeDateTime(updated?.asOf),
                    patientId: patient
                }
            }
        }).then(handleUpdate);
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard title="Sex & Birth" data={tableData} editing={editing} onEdit={() => isEditing(true)}>
                <SexBirthForm entry={entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
