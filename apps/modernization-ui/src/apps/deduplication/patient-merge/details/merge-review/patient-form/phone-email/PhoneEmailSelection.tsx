import { MergeCandidate, MergePhoneEmail } from 'apps/deduplication/api/model/MergeCandidate';
import { useState } from 'react';
import { DetailsRow } from '../shared/section/DetailsRow';
import { Section } from '../shared/section/Section';
import { PhoneEmailDataTable } from './phone-email-data-table/PhoneEmailDataTable';
import { PhoneEmailDetails } from './phone-email-details/PhoneEmailDetails';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const PhoneEmailSelection = ({ mergeCandidates }: Props) => {
    const [selectedPhoneEmail, setSelectedPhoneEmail] = useState(
        new Map<string, MergePhoneEmail | undefined>(mergeCandidates.map((p) => [p.personUid, undefined]))
    );

    const handleViewPhoneEmail = (personUid: string, phoneEmail: MergePhoneEmail) => {
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
                mergeCandidates={mergeCandidates}
                render={(p) => (
                    <PhoneEmailDataTable
                        patientData={p}
                        onViewPhoneEmail={(n) => handleViewPhoneEmail(p.personUid, n)}
                        selectedPhoneEmail={selectedPhoneEmail.get(p.personUid)}
                    />
                )}
            />
            <DetailsRow
                id="patient-phone-email"
                mergeCandidates={mergeCandidates}
                render={(p) => {
                    const phoneEmail = selectedPhoneEmail.get(p.personUid);
                    return phoneEmail && <PhoneEmailDetails phoneEmail={phoneEmail} />;
                }}
            />
        </>
    );
};
