import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientGeneral } from 'generated/graphql/schema';
import { HorizontalTable } from 'components/Table/HorizontalTable';
import { format } from 'date-fns';
import { useFindPatientProfileGeneral } from './useFindPatientProfileGeneral';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const GeneralPatient = ({ patient }: PatientLabReportTableProps) => {
    const [generalTableData, setGeneralTableData] = useState<any>();
    const handleComplete = (data: FindPatientProfileQuery) => {
        setGeneralTableData([
            {
                title: 'As of:',
                text: data?.findPatientProfile?.general
                    ? format(new Date(data?.findPatientProfile?.general?.asOf), 'MM/dd/yyyy')
                    : ''
            },
            { title: 'Marital status:', text: data?.findPatientProfile?.general?.maritalStatus?.description || '' },
            { title: 'Mother’s maiden name:', text: data?.findPatientProfile?.general?.maternalMaidenName || '' },
            { title: 'Number of adults in residence:', text: data?.findPatientProfile?.general?.adultsInHouse || '' },
            {
                title: 'Number of children in residence:',
                text: data?.findPatientProfile?.general?.childrenInHouse || ''
            },
            { title: 'Primary occupation:', text: data?.findPatientProfile?.general?.occupation?.description || '' },
            {
                title: 'Highest level of education:',
                text: data?.findPatientProfile?.general?.educationLevel?.description || ''
            },
            { title: 'Primary language:', text: data?.findPatientProfile?.general?.primaryLanguage?.description || '' },
            { title: 'Speaks english:', text: data?.findPatientProfile?.general?.speaksEnglish?.description || '' },
            { title: 'State HIV case ID:', text: data?.findPatientProfile?.general?.stateHIVCase || '' }
        ]);
    };

    const [getProfile, { data }] = useFindPatientProfileGeneral({ onCompleted: handleComplete });

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
                data={data?.findPatientProfile?.general as PatientGeneral}
                type="general"
                tableHeader="General Patient Information"
                tableData={generalTableData}
            />
        </Grid>
    );
};
