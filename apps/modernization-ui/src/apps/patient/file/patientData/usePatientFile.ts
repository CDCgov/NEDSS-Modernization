import { useEffect, useState } from 'react';
import { Patient } from './Patient';
import { PatientProfileResult, useFindPatientProfileSummary } from './useFindPatientProfileSummary';
import { PatientSummary } from 'generated/graphql/schema';

export type Profile = {
    patient: Patient;
    summary?: PatientSummary;
};

export const usePatientFile = (patientId?: string) => {
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
        if (patientId) {
            getProfile({
                variables: {
                    shortId: +patientId
                },
                notifyOnNetworkStatusChange: true
            });
        }
    }, [patientId]);

    return { patient: profile?.patient, summary: profile?.summary, refetch };
};
