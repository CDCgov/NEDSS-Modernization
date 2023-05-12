import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { FindPatientProfileQuery, PatientEthnicity } from 'generated/graphql/schema';
import { HorizontalTable } from 'components/Table/HorizontalTable';
import { format } from 'date-fns';
import { useFindPatientProfileEthnicity } from './useFindPatientProfileMortality';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const Ethnicity = ({ patient }: PatientLabReportTableProps) => {
    const [generalTableData, setGeneralTableData] = useState<any>();
    const handleComplete = (data: FindPatientProfileQuery) => {
        setGeneralTableData([
            {
                title: 'As of:',
                text: data?.findPatientProfile?.ethnicity
                    ? format(new Date(data?.findPatientProfile?.ethnicity?.asOf), 'MM/dd/yyyy')
                    : ''
            },
            { title: 'Ethnicity::', text: data?.findPatientProfile?.ethnicity?.ethnicGroup?.description || '' },
            {
                title: 'Spanish origin:',
                text:
                    data?.findPatientProfile?.ethnicity?.detailed &&
                    data?.findPatientProfile?.ethnicity?.detailed?.length > 0
                        ? data?.findPatientProfile?.ethnicity?.detailed.map((detail, index: number) => (
                              <span key={detail?.id}>
                                  {detail?.description}{' '}
                                  {data?.findPatientProfile?.ethnicity?.detailed &&
                                      data?.findPatientProfile?.ethnicity?.detailed?.length - 1 !== index &&
                                      '|'}{' '}
                              </span>
                          ))
                        : ''
            },
            {
                title: 'Reasons unknown:',
                text: data?.findPatientProfile?.ethnicity?.unknownReason?.description || ''
            }
        ]);
    };

    const [getProfile, { data }] = useFindPatientProfileEthnicity({ onCompleted: handleComplete });

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
                data={data?.findPatientProfile?.ethnicity as PatientEthnicity}
                type="ethnicity"
                tableHeader="Ethnicity"
                tableData={generalTableData}
            />
        </Grid>
    );
};
