import { FindPatientProfileQuery, useFindPatientProfileLazyQuery } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

type Result = FindPatientProfileQuery['findPatientProfile'];

const initialPage = {
    pageNumber: 0,
    pageSize: TOTAL_TABLE_DATA
};

export const usePatientProfileAPI = (patient?: string) => {
    const [profile, setProfile] = useState<Result>();

    const handleComplete = (data: FindPatientProfileQuery) => {
        if (data?.findPatientProfile) {
            setProfile(data.findPatientProfile);
        }
    };

    const [getProfile] = useFindPatientProfileLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient,
                    page: initialPage,
                    page1: initialPage,
                    page2: initialPage,
                    page3: initialPage,
                    page4: initialPage,
                    page5: initialPage
                }
            });
        }
    }, [patient]);

    return profile;
};
