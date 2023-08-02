import { useEffect, useState } from 'react';
import { Patient } from './Patient';
import { PatientSummary } from 'pages/patient/profile/summary';
import { PatientProfileResult, useFindPatientProfileSummary } from './useFindPatientProfileSummary';

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

    const [getProfile] = useFindPatientProfileSummary({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    shortId: +patient
                }
            });
        }
    }, [patient]);

    let PatientLegalNameObject = {
        __typename: 'PatientLegalName',
        prefix: '',
        first: 'Mike',
        middle: 'Greg',
        last: 'Davis',
        suffix: ''
    };

    let PatientSummaryAddressObject = {
        __typename: 'PatientSummaryAddress',
        street: 'string',
        city: 'string',
        state: 'sting',
        zipcode: 'string',
        country: 'string '
    };


    let PatientSummaryPhoneObject = { __typename: 'PatientSummaryPhone', use: 'string', number: '555-5555555' };
    let PatientSummaryEmailObject = { __typename: 'PatientSummaryEmail', use: 'string', address: 'string' };

    let PatientSummaryObject = {
        __typename: 'PatientSummary',
        birthday: '01/01/1980',
        age: 43,
        gender: 'Female',
        ethnicity: 'non-hipanic',
        race: 'white',
        legalName: PatientLegalNameObject,
        phone: PatientSummaryPhoneObject,
        email: PatientSummaryEmailObject,
        address: PatientSummaryAddressObject,
    };

    let PatientProfileObject = {
        __typename: 'PatientProfile',
        id: 'string',
        local: 'string',
        shortId: 80201,
        version: 'number',
        status: 'string',
        deletable: false,
        summary: PatientSummaryObject,
        deceased: false
    };


    return PatientProfileObject;
};
