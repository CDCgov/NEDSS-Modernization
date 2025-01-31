import { RefObject } from 'react';
import { ModalRef } from '@trussworks/react-uswds';
import { AddPatientRaceMutation, useAddPatientRaceMutation } from 'generated/graphql/schema';
import { EntryModal } from 'apps/patient/profile/entry';
import { RaceEntry } from './RaceEntry';
import { RaceEntryForm } from './RaceEntryForm';
import { externalizeDate } from 'date';

type PatientRaceAddProps = {
    modal: RefObject<ModalRef>;
    patient: number;
    entry: Partial<RaceEntry>;
    onSuccess: () => void;
    onFailure: (reason: string) => void;
    onCancel: () => void;
};

const AddPatientRace = ({ modal, patient, entry, onSuccess, onFailure, onCancel }: PatientRaceAddProps) => {
    const [add] = useAddPatientRaceMutation();

    const handleResponse = (data?: AddPatientRaceMutation | null) => {
        if (data?.addPatientRace.__typename === 'PatientRaceChangeFailureExistingCategory') {
            onFailure('The Patient has an existing race demographic for that category.');
        } else {
            onSuccess();
        }
    };

    const handleChanged = (entry: RaceEntry) => {
        if (entry.category) {
            add({
                variables: {
                    input: {
                        patient,
                        asOf: externalizeDate(entry.asOf),
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            })
                .then((response) => handleResponse(response.data))
                .catch(() => onFailure("An unexpected error occurred when adding a patient's race"));
        }
    };

    return (
        <EntryModal onClose={onCancel} modal={modal} id="add-patient-race-entry" title="Add - Race" overflow>
            <RaceEntryForm patient={patient} entry={entry} onChange={handleChanged} />
        </EntryModal>
    );
};

export { AddPatientRace };
