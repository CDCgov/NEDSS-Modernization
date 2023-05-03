import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientBirth } from 'generated/graphql/schema';
import { HorizontalTable } from 'components/Table/HorizontalTable';
import { format } from 'date-fns';
import { useFindPatientProfileBirth } from './useFindPatientProfileBirth';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const SexBirth = ({ patient }: PatientLabReportTableProps) => {
    const [generalTableData, setGeneralTableData] = useState<any>();
    const handleComplete = (data: FindPatientProfileQuery) => {
        console.log(
            'data?.findPatientProfile?.birth && data?.findPatientProfile?.gender:',
            data?.findPatientProfile?.birth,
            data?.findPatientProfile?.gender
        );
        if (data?.findPatientProfile?.birth && data?.findPatientProfile?.gender) {
            setGeneralTableData([
                { title: 'As of:', text: format(new Date(data?.findPatientProfile?.birth.asOf), 'MM/dd/yyyy') },
                { title: 'Current age:', text: data?.findPatientProfile?.birth?.age },
                { title: 'Current sex:', text: data?.findPatientProfile?.gender?.current?.description },
                { title: 'Unknown reason:', text: data?.findPatientProfile?.gender?.unknownReason?.description },
                { title: 'Transgender information:', text: data?.findPatientProfile?.gender?.preferred?.description },
                { title: 'Additional gender:', text: data?.findPatientProfile?.gender?.additional },
                { title: 'Birth sex:', text: data?.findPatientProfile?.gender?.birth?.description },
                { title: 'Multiple birth:', text: data?.findPatientProfile?.birth?.multipleBirth?.description },
                { title: 'Birth order:', text: data?.findPatientProfile?.birth?.birthOrder },
                { title: 'Birth city:', text: data?.findPatientProfile?.birth?.city },
                { title: 'Birth state:', text: data?.findPatientProfile?.birth?.state?.description },
                { title: 'Birth county:', text: data?.findPatientProfile?.birth?.county?.description },
                { title: 'Birth country:', text: data?.findPatientProfile?.birth?.country?.description }
            ]);
        }
    };

    const [getProfile, { data }] = useFindPatientProfileBirth({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient
                }
            });
        }
    }, [patient]);

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <HorizontalTable
                data={data?.findPatientProfile?.birth as PatientBirth}
                type="sex"
                tableHeader="Sex & birth"
                tableData={generalTableData}
            />
        </Grid>
    );
};
