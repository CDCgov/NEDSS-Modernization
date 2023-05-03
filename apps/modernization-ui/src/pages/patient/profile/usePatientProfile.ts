import { FindPatientProfileQuery } from 'generated/graphql/schema';
import { useEffect, useState } from 'react';
import { Patient } from './Patient';
import { transform as patientTransformer } from './PatientTransformer';
import { PatientSummary, transform as summaryTransformer } from 'pages/patient/profile/summary';
import { useFindPatientProfileSummary } from './useFindPatientProfileSummary';

export type Profile = {
    patient: Patient;
    summary?: PatientSummary;
};

export const usePatientProfile = (patient?: string) => {
    const [profile, setProfile] = useState<Profile>();

    const handleComplete = (data: FindPatientProfileQuery) => {
        if (data?.findPatientProfile) {
            const patient = patientTransformer(data.findPatientProfile);

            if (patient) {
                const summary = summaryTransformer(data.findPatientProfile.summary);
                setProfile({ patient, summary });
            }
        }
    };

    const [getProfile] = useFindPatientProfileSummary({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient
                }
            });
        }
    }, [patient]);

    return profile;
};
