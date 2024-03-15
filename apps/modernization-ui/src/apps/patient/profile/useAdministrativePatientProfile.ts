import { useEffect, useState } from 'react';
import { Patient } from './Patient';
import { PatientAdministrative } from 'generated/graphql/schema';
import {
    PatientProfileAdministrativeResult,
    useFindPatientProfileAdministrative
} from './administrative/useFindPatientProfileAdministrative';
import { sortingByDate } from 'sorting/sortingByDate';
import { TOTAL_TABLE_DATA } from 'utils/util';

export type Profile = {
    patient: Patient;
    summary?: PatientProfileAdministrativeResult;
};

export const useAdministrativePatientProfile = (patientId?: string) => {
    const [profile] = useState<Profile>();
    const [total, setTotal] = useState<number>(0);
    const [administratives, setAdministratives] = useState<PatientAdministrative[]>([]);

    const handleComplete = (data: PatientProfileAdministrativeResult) => {
        setTotal(data?.findPatientProfile?.administrative?.total ?? 0);
        setAdministratives(
            sortingByDate(data?.findPatientProfile?.administrative?.content || []) as Array<PatientAdministrative>
        );
    };

    const [fetch, { refetch }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });

    useEffect(() => {
        patientId &&
            fetch({
                variables: {
                    patient: patientId,
                    page: {
                        pageNumber: 0,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
    }, [1, patientId]);

    return { patient: profile?.patient, summary: profile?.summary, refetch, total, administratives };
};
