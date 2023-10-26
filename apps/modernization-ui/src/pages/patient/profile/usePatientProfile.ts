import { useEffect, useState } from 'react';
import { Patient } from './Patient';
import { PatientProfileResult, useFindPatientProfileSummary } from './useFindPatientProfileSummary';
import { PatientSummary } from 'generated/graphql/schema';

export type Profile = {
    patient: Patient;
    summary?: PatientSummary;
};

export const usePatientProfile = (patient?: string) => {
    const [profile, setProfile] = useState<Profile>();

    const handleComplete = (data: PatientProfileResult) => {
        if (data?.findPatientProfile) {
            const patient = data.findPatientProfile;

            if (patient) {
                const summary = data.findPatientProfile.summary;
                setProfile({ patient, summary });
            }
        }
    };

    const [getProfile, { refetch }] = useFindPatientProfileSummary({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    shortId: +patient
                },
                notifyOnNetworkStatusChange: true
            });
        }
    }, [patient]);

    return { profile, refetch };
};
