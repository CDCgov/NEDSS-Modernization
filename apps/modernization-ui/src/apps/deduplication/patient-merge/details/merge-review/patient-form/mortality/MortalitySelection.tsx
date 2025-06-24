import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { Mortality } from './mortality/Mortality';
import { useFormContext, useWatch } from 'react-hook-form';
import { PatientMergeForm } from '../../model/PatientMergeForm';
import { useEffect, useState } from 'react';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const MortalitySelection = ({ mergeCandidates }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const selectedPerson = useWatch({ control: form.control, name: 'mortality.asOf' });
    const [allowDetailedSelection, setAllowDetailedSelection] = useState<boolean>(false);
    const [previousWasDeceased, setPreviousWasDeceased] = useState<boolean>(true);

    useEffect(() => {
        // If the patient selected is deceased, and other entries with deceased 'Yes' exist, allow detailed selection
        const deceasedEntries = mergeCandidates.filter((m) => m.mortality.deceased === 'Yes');
        setAllowDetailedSelection(
            deceasedEntries.length > 1 && deceasedEntries.some((d) => d.personUid === selectedPerson)
        );

        // going from deceased 'No' | undefined to anything else should update all values
        // going from deceased 'Yes' to 'No' | undefined should update all values
        // going from deceased 'Yes' to 'Yes' does NOT update all values
        const newIsDeceased = deceasedEntries.some((m) => m.personUid === selectedPerson);
        if (!previousWasDeceased || !newIsDeceased) {
            form.setValue('mortality.deceased', selectedPerson);
            form.setValue('mortality.dateOfDeath', selectedPerson);
            form.setValue('mortality.deathCity', selectedPerson);
            form.setValue('mortality.deathState', selectedPerson);
            form.setValue('mortality.deathCountry', selectedPerson);
        }

        setPreviousWasDeceased(newIsDeceased);
    }, [selectedPerson]);
    return (
        <Section
            title="MORTALITY"
            mergeCandidates={mergeCandidates}
            render={(p) => (
                <Mortality
                    personUid={p.personUid}
                    mortality={p.mortality}
                    allowDetailedSelection={allowDetailedSelection}
                />
            )}
        />
    );
};
