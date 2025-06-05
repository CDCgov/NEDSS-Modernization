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
        // If the patient selected is deceased, allow detailed selection
        const mergeCandidate = mergeCandidates.find(
            (m) => m.personUid === selectedPerson && m.mortality.deceased === 'Yes'
        );
        setAllowDetailedSelection(mergeCandidate !== undefined);

        // going from deceased 'No' | undefined to anything else should update all values
        // going from deceased 'Yes' to 'No' | undefined should update all values
        // going from deceased 'Yes' to 'Yes' does NOT update all values
        if (!previousWasDeceased || (previousWasDeceased && mergeCandidate?.mortality.deceased !== 'Yes')) {
            form.setValue('mortality.dateOfDeath', selectedPerson);
            form.setValue('mortality.deathCity', selectedPerson);
            form.setValue('mortality.deathState', selectedPerson);
            form.setValue('mortality.deathCountry', selectedPerson);
        }

        setPreviousWasDeceased(mergeCandidate?.mortality.deceased === 'Yes');
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
