import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientMortality } from 'generated/graphql/schema';
import { HorizontalTable } from 'components/Table/HorizontalTable';
import { format } from 'date-fns';
import { useFindPatientProfileMortality } from './useFindPatientProfileMortality';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const Mortality = ({ patient }: PatientLabReportTableProps) => {
    const [generalTableData, setGeneralTableData] = useState<any>();
    const handleComplete = (data: FindPatientProfileQuery) => {
        console.log('data:', data);
        setGeneralTableData([
            {
                title: 'As of:',
                text: data?.findPatientProfile?.mortality?.asOf
                    ? format(new Date(data?.findPatientProfile?.mortality?.asOf), 'MM/dd/yyyy')
                    : ''
            },
            {
                title: 'Is the patient deceased:',
                text: data?.findPatientProfile?.mortality?.deceased?.description || ''
            },
            { title: 'Date of death:', text: data?.findPatientProfile?.mortality?.deceasedOn || '' },
            { title: 'City of death:', text: data?.findPatientProfile?.mortality?.city || '' },
            { title: 'State of death:', text: data?.findPatientProfile?.mortality?.state?.description || '' },
            { title: 'County of death:', text: data?.findPatientProfile?.mortality?.county?.description || '' },
            { title: 'Country of death:', text: data?.findPatientProfile?.mortality?.country?.description || '' }
        ]);
    };

    const [getProfile, { data }] = useFindPatientProfileMortality({ onCompleted: handleComplete });
    console.log('generalTableData:', generalTableData);

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    shortId: +patient
                }
            });
        }
    }, [patient]);

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <HorizontalTable
                data={data?.findPatientProfile?.mortality as PatientMortality}
                type="mortality"
                tableHeader="Mortality"
                tableData={generalTableData}
            />
        </Grid>
    );
};
