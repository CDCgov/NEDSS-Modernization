import { dummyPatientData, getLatestLegalName } from '../dummyData';
import { PatientSummaryCard } from './PatientSummary/PatientSummaryCard';

export const PreviewHeader = () => {
    const { patientId } = dummyPatientData;

    // Extract fields for summary card
    const latestLegalName = getLatestLegalName(dummyPatientData.name);

    const patientSummaryDummy = {
        firstName: latestLegalName?.first || '---',
        lastName: latestLegalName?.last || '---',
        dob: dummyPatientData.selectedSexAndBirth.dateOfBirth || '---',
        age: dummyPatientData.selectedSexAndBirth.currentAge ?? '---',
        gender: dummyPatientData.selectedSexAndBirth.birthSex
    };

    const { firstName, lastName, dob, age, gender } = patientSummaryDummy;

    return (
        <PatientSummaryCard
            firstName={firstName}
            lastName={lastName}
            dob={dob}
            age={age}
            gender={gender}
            patientId={patientId}
            separator
        />
    );
};
