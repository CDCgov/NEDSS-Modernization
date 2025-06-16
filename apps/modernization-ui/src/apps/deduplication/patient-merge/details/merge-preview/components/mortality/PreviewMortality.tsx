import React from 'react';
import { format, parseISO, isValid } from 'date-fns';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';

export const PreviewMortality = ({
    mergeFormData,
    mergeCandidates
}: {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
}) => {
    const getField = (uid?: string, field?: keyof MergeCandidate['mortality']) =>
        uid && field ? (mergeCandidates.find((mc) => mc.personUid === uid)?.mortality?.[field] ?? '---') : '---';

    const parseDate = (str?: string) => {
        const d = str ? parseISO(str) : undefined;
        return isValid(d) ? d : undefined;
    };

    const m = mergeFormData.mortality;
    const asOfDate = parseDate(m.asOf);

    const items = [
        { label: 'As of', text: asOfDate ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Is the patient deceased', text: getField(m.deceased, 'deceased'), lined: true },
        { label: 'Date of death', text: getField(m.dateOfDeath, 'dateOfDeath'), lined: true },
        { label: 'City of death', text: getField(m.deathCity, 'deathCity'), lined: true },
        { label: 'State of death', text: getField(m.deathState, 'deathState'), lined: true },
        { label: 'County of death', text: getField(m.deathState, 'deathCounty'), lined: true },
        { label: 'County of death', text: getField(m.deathState, 'deathCounty'), lined: true },
        { label: 'Country of death', text: getField(m.deathCountry, 'deathCountry'), lined: false }
    ];

    return <LinedMergePreviewCard id="mortality" title="Mortality" items={items} />;
};
