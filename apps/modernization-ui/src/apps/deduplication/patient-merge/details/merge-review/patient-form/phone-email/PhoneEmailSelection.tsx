import { PatientData, PatientPhoneEmail } from 'apps/deduplication/api/model/PatientData';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { PhoneEmailDataTable } from './phone-email-data-table/PhoneEmailDataTable';
import { PhoneEmailDetails } from './phone-email-details/PhoneEmailDetails';

type Props = {
    patientData: PatientData[];
};
export const PhoneEmailSelection = ({ patientData }: Props) => {
    const [selectedPhoneEmail, setSelectedPhoneEmail] = useState(
        new Map<string, PatientPhoneEmail | undefined>(patientData.map((p) => [p.personUid, undefined]))
    );

    const handleViewPhoneEmail = (personUid: string, phoneEmail: PatientPhoneEmail) => {
        const map = new Map(selectedPhoneEmail);
        if (map.get(personUid) === phoneEmail) {
            map.set(personUid, undefined);
        } else {
            map.set(personUid, phoneEmail);
        }
        setSelectedPhoneEmail(map);
    };

    return (
        <>
            <Section
                title="PHONE & EMAIL"
                patientData={patientData}
                render={(p) => (
                    <PhoneEmailDataTable
                        patientData={p}
                        onViewPhoneEmail={(n) => handleViewPhoneEmail(p.personUid, n)}
                        selectedPhoneEmail={selectedPhoneEmail.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-name"
                patientData={patientData}
                render={(p) => {
                    const phoneEmail = selectedPhoneEmail.get(p.personUid);
                    return phoneEmail && <PhoneEmailDetails phoneEmail={phoneEmail} />;
                }}
            />
        </>
    );
};
